package com.algaworks.algashop.billing.domain.model.invoice.payment;

import com.algaworks.algashop.billing.domain.model.FieldValidations;
import com.algaworks.algashop.billing.domain.model.invoice.PaymentMethod;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
public class Payment {

    private String gatewayCode;

    private UUID invoicedId;

    private PaymentMethod method;

    private PaymentStatus status;

    public Payment(String gatewayCode, UUID invoicedId, PaymentMethod method, PaymentStatus status) {
        FieldValidations.requiresNonBlank(gatewayCode);
        Objects.requireNonNull(invoicedId);
        Objects.requireNonNull(method);
        Objects.requireNonNull(status);
        this.gatewayCode = gatewayCode;
        this.invoicedId = invoicedId;
        this.method = method;
        this.status = status;
    }
}
