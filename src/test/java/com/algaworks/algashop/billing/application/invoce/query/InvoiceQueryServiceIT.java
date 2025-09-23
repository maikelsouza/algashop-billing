package com.algaworks.algashop.billing.application.invoce.query;

import com.algaworks.algashop.billing.domain.model.invoice.Invoice;
import com.algaworks.algashop.billing.domain.model.invoice.InvoiceRepository;
import com.algaworks.algashop.billing.domain.model.invoice.InvoiceTestDataBuilder;
import com.algaworks.algashop.billing.domain.model.invoice.PaymentMethod;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class InvoiceQueryServiceIT {



    @Autowired
    private InvoiceQueryService invoiceQueryService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Test
    public void shouldFindByOrderId(){
        Invoice invoice = InvoiceTestDataBuilder.anInvoice().build();
        invoice.changePaymentSettings(PaymentMethod.GATEWAY_BALANCE, null);
        invoiceRepository.saveAndFlush(invoice);
        InvoiceOutput invoiceOut = invoiceQueryService.findOrderById(invoice.getOrderId());

        Assertions.assertThat(invoiceOut.getId()).isEqualTo(invoice.getId());
    }


}