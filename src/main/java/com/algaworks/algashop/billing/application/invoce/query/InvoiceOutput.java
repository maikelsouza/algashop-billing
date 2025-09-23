package com.algaworks.algashop.billing.application.invoce.query;

import com.algaworks.algashop.billing.application.invoce.management.PayerData;
import com.algaworks.algashop.billing.domain.model.invoice.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceOutput {

    private UUID id;

    private String orderId;

    private UUID customerId;

    private OffsetDateTime issuedAt;

    private OffsetDateTime paidAt;

    private OffsetDateTime canceledAt;

    private OffsetDateTime expiredAt;

    private BigDecimal totalAmount;

    private InvoiceStatus status;

    private PayerData payer;

    private PaymentSettingsOutput paymentSettings;



}
