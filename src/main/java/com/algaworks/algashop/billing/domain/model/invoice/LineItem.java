package com.algaworks.algashop.billing.domain.model.invoice;

import lombok.*;

import java.math.BigDecimal;

@Setter(AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LineItem {

    private Integer number;

    private String name;

    private BigDecimal amount;
}
