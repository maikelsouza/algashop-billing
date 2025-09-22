package com.algaworks.algashop.billing.infrastrure.persistence;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateNameConfiguration {

    @Bean
    public ImplicitNamingStrategy implicit(){
        return new ImplicitNamingStrategyComponentPathImpl();
    }
}
