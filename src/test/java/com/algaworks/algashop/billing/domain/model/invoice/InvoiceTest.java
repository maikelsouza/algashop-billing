package com.algaworks.algashop.billing.domain.model.invoice;

import com.algaworks.algashop.billing.domain.model.DomainException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class InvoiceTest {

    @Test
    public void shouldGenerateInvoice(){
        String orderId = "111";
        UUID customerId = UUID.randomUUID();
        Payer payer = InvoiceTestDataBuilder.aPayer();
        Set<LineItem> items = Set.of(
                InvoiceTestDataBuilder.aLineItem(),
                InvoiceTestDataBuilder.aLineItemAlt()
        );

        Invoice invoice = Invoice.issue(orderId, customerId, payer, items);

        BigDecimal expectedTotalAmount = invoice.getItems()
                .stream()
                .map(LineItem::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertThat(invoice)
                .satisfies(i -> {
                    assertThat(i.getId()).isNotNull();
                    assertThat(i.getOrderId()).isNotBlank();
                    assertThat(i.getCustomerId()).isNotNull();
                    assertThat(i.getPaidAt()).isNull();
                    assertThat(i.getCanceledAt()).isNull();
                    assertThat(i.getCancelReason()).isNull();
                    assertThat(i.getExpiresAt()).isNotNull();
                    assertThat(i.getTotalAmount()).isEqualTo(expectedTotalAmount);
                    assertThat(i.getStatus()).isEqualTo(InvoiceStatus.UNPAID);
                    assertThat(i.getPaymentSettings()).isNull();
                    assertThat(i.getPayer()).isNotNull();
                    assertThat(i.getItems()).isNotEmpty();
                    assertThat(i.getItems()).hasSize(2); });
    }

    @Test
    public void shouldThrowExceptionWhenTryingToModifyItemsSet() {
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        Set<LineItem> items = invoice.getItems();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(items::clear);
    }

    @Test
    public void shouldMarkInvoiceAsPaid(){
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        invoice.markAsPaid();

        assertThat(invoice)
                .satisfies(i->{
                   assertThat(i.getPaidAt()).isNotNull();
                   assertThat(i.getStatus()).isEqualTo(InvoiceStatus.PAID);
                   assertThat(i.isUnpaid()).isFalse();
                });
    }

    @Test
    public void shouldMarkInvoiceCanceled(){
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        String cancelReason = "Cancellation reason";

        invoice.cancel(cancelReason);

        assertThat(invoice).
              satisfies( i -> {
                assertThat(i.getCanceledAt()).isNotNull();
                assertThat(i.getStatus()).isEqualTo(InvoiceStatus.CANCELED);
                assertThat(i.getCancelReason()).isNotNull();
                assertThat(i.getCancelReason()).isNotBlank();
                assertThat(i.getCancelReason()).isEqualTo(cancelReason);
              });
    }

    @Test
    public void shouldChangePaymentSettings(){
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        UUID creditCardId = UUID.randomUUID();
        invoice.changePaymentSettings(PaymentMethod.CREDIT_CARD, creditCardId);

        assertThat(invoice).
            satisfies( i -> {
                assertThat(i.getPaymentSettings()).isNotNull();
                assertThat(i.getPaymentSettings().getCreditCardId()).isNotNull();
                assertThat(i.getPaymentSettings().getMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
            });
    }

    @Test
    public void shouldAssignPaymentGatewayCode(){
        UUID uuid = UUID.randomUUID();
        Invoice invoice = InvoiceTestDataBuilder.anInvoice()
                .paymentSettings(PaymentMethod.CREDIT_CARD, uuid).build();
        String gatewayCode = "code-from-gateway";

        invoice.assignPaymentGatewayCode(gatewayCode);
        assertThat(invoice).
                satisfies( i -> {
                    assertThat(i.getPaymentSettings()).isNotNull();
                    assertThat(i.getPaymentSettings().getCreditCardId()).isNotNull();
                    assertThat(i.getPaymentSettings().getCreditCardId()).isEqualTo(uuid);
                    assertThat(i.getPaymentSettings().getMethod()).isEqualTo(PaymentMethod.CREDIT_CARD);
                    assertThat(i.getPaymentSettings().getGatewayCode()).isEqualTo(gatewayCode);
                });
    }

    @Test
    public void shouldGenerationIllegalArgumentExceptionWhenTryCreateInvoice(){
        String orderId = "111";
        UUID customerId = UUID.randomUUID();
        Payer payer = InvoiceTestDataBuilder.aPayer();

        assertThatThrownBy(() -> Invoice.issue(orderId, customerId, payer, new HashSet<>()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("LineItems is empty");

    }

    @Test
    public void shouldGenerateDomainExceptionWhenTryMarkPaidAnInvoiceCanceled(){
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        invoice.cancel("Cancellation reason");

        assertThatThrownBy(invoice::markAsPaid)
                .isInstanceOf(DomainException.class)
                .hasMessage(String.format("Invoice %s with status %s cannot be marked as paid",
                        invoice.getId(), invoice.getStatus().toString().toLowerCase()));
    }

    @Test
    public void shouldGenerateDomainExceptionWhenTryAnInvoiceCanceled(){
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        invoice.cancel("Cancellation reason");

        assertThatThrownBy(() -> invoice.cancel("Cancellation reason"))
                .isInstanceOf(DomainException.class)
                .hasMessage(String.format("Invoice %s is already canceled", invoice.getId()));
    }

    @Test
    public void shouldGenerateDomainExceptionWhenTryChangePaymentSettings(){
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        invoice.markAsPaid();

        assertThatThrownBy(() -> invoice.changePaymentSettings(PaymentMethod.CREDIT_CARD, UUID.randomUUID()))
                .isInstanceOf(DomainException.class)
                .hasMessage(String.format("Invoice %s with status %s cannot be edited",
                        invoice.getId(), invoice.getStatus().toString().toLowerCase()));
    }

    @Test
    public void shouldGenerateDomainExceptionWhenTryAssignPaymentGatewayCode(){
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        invoice.markAsPaid();
        String gatewayCode = "code-from-gateway";

        assertThatThrownBy(() -> invoice.assignPaymentGatewayCode(gatewayCode))
                .isInstanceOf(DomainException.class)
                .hasMessage(String.format("Invoice %s with status %s cannot be edited",
                        invoice.getId(), invoice.getStatus().toString().toLowerCase()));
    }

    @Test
    public void shouldGenerateUnsupportedOperationExceptionWhenTryClearItemsInvoice(){
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();

        assertThatThrownBy(() -> invoice.getItems().clear())
                .isInstanceOf(UnsupportedOperationException .class);
    }

}