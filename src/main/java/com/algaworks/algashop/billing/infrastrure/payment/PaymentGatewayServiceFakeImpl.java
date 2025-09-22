package com.algaworks.algashop.billing.infrastrure.payment;

import com.algaworks.algashop.billing.domain.model.invoice.PaymentMethod;
import com.algaworks.algashop.billing.domain.model.invoice.payment.Payment;
import com.algaworks.algashop.billing.domain.model.invoice.payment.PaymentGatewayService;
import com.algaworks.algashop.billing.domain.model.invoice.payment.PaymentRequest;
import com.algaworks.algashop.billing.domain.model.invoice.payment.PaymentStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentGatewayServiceFakeImpl implements PaymentGatewayService {

    @Override
    public Payment capture(PaymentRequest request) {
        return Payment.builder()
                .invoicedId(request.getInvoiceId())
                .status(PaymentStatus.PAID)
                .method(request.getMethod())
                .gatewayCode(UUID.randomUUID().toString())
                .build();
    }

    @Override
    public Payment paymentFindByCody(String gatewayCode) {
        return Payment.builder()
                .invoicedId(UUID.randomUUID())
                .status(PaymentStatus.PAID)
                .method(PaymentMethod.GATEWAY_BALANCE)
                .gatewayCode(UUID.randomUUID().toString())
                .build();
    }
}
