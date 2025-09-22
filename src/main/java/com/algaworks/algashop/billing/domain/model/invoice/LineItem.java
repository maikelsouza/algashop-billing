package com.algaworks.algashop.billing.domain.model.invoice;

import com.algaworks.algashop.billing.domain.model.FieldValidations;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Setter(AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LineItem {

    private Integer number;

    private String name;

    private BigDecimal amount;

    @Builder
    public LineItem(Integer number, String name, BigDecimal amount) {
        FieldValidations.requiresNonBlank(name);
        Objects.requireNonNull(number);
        Objects.requireNonNull(amount);

        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount cannot be less than zero");
        }
        if(number <= 0){
            throw new IllegalArgumentException("Number cannot be less than zero");
        }

        this.number = number;
        this.name = name;
        this.amount = amount;
    }
}
