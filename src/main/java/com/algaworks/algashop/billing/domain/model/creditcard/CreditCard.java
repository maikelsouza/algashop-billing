package com.algaworks.algashop.billing.domain.model.creditcard;

import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CreditCard {

    @EqualsAndHashCode.Include
    private UUID id;

    private OffsetDateTime createdAt;

    private UUID customerId;

    private String lastNumbers;

    private String brand;

    private Integer exMonth;

    private Integer exYear;

    private String gatewayCode;

}
