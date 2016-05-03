package com.sky.test.controllers;

import com.sky.test.services.CatalogueService;
import com.sky.test.services.CustomerLocationService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestBeans {

    @Bean
    @Primary
    CatalogueService testCatalogueService() {
        return Mockito.mock(CatalogueService.class);
    }

    @Bean
    @Primary
    CustomerLocationService testCustomerLocationService() {
        return Mockito.mock(CustomerLocationService.class);
    }
}
