package com.algaworks.algashop.billing.application.invoce.query;

public interface InvoiceQueryService {

    InvoiceOutput findOrderById(String orderId);
}
