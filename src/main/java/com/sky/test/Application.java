package com.sky.test;

import com.sky.test.services.CatalogueService;
import com.sky.test.services.CustomerLocationService;
import com.sky.test.services.HardCodedCatalogueService;
import com.sky.test.services.HardCodedCustomerLocationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Bean
    CatalogueService getCatalogueService() {
        return new HardCodedCatalogueService();
    }

    @Bean
    CustomerLocationService getCustomerLocationService() {
        return new HardCodedCustomerLocationService();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
