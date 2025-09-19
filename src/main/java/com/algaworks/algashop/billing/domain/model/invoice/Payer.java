package com.algaworks.algashop.billing.domain.model.invoice;

import lombok.*;

@Setter(AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payer {

    private String fullName;

    private String document;

    private String phone;

    private String email;

    private Address address;


}
