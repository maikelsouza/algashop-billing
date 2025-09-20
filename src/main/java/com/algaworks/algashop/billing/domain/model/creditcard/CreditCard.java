package com.algaworks.algashop.billing.domain.model.creditcard;

import com.algaworks.algashop.billing.domain.model.IdGenerator;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.OffsetDateTime;
import java.util.Objects;
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

    private String gatewayCode;

    public static CreditCard brandNew(UUID customerId, String lastNumbers, String brand,
                                      Integer exMonth, Integer exYear, String gatewayCode){

        Objects.requireNonNull(customerId);
        Objects.requireNonNull(exMonth);
        Objects.requireNonNull(exYear);

        if(StringUtils.isAnyBlank(lastNumbers, brand, gatewayCode)){
            throw new IllegalArgumentException();
        }

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

    public void setGatewayCode(String gatewayCode) {
        if(StringUtils.isBlank(gatewayCode)){
            throw new IllegalArgumentException("Gateway Code is blank");
        }
        this.gatewayCode = gatewayCode;
    }
}
