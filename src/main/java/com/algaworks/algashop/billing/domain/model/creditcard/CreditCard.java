package com.algaworks.algashop.billing.domain.model.creditcard;

import com.algaworks.algashop.billing.domain.model.IdGenerator;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Setter(AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CreditCard {

    @EqualsAndHashCode.Include
    private UUID id;

    private OffsetDateTime createdAt;

    private UUID customerId;

    private String lastNumbers;

    private String brand;

    private Integer exMonth;

    private Integer exYear;

    @Setter(AccessLevel.PUBLIC)
    private String gatewayCode;

    public static CreditCard brandNew(UUID customerId, String lastNumbers, String brand,
                                      Integer exMonth, Integer exYear, String gatewayCode){
        return new CreditCard(IdGenerator.generateTimeBasedUUID(),
                OffsetDateTime.now(),
                customerId,
                lastNumbers,
                brand,
                exMonth,
                exYear,
                gatewayCode
                );
     }
}
