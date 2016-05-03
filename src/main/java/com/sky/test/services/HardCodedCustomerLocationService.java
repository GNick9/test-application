package com.sky.test.services;

import com.sky.test.model.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Simple implementation of the CustomerLocationService to serve up 3 hardcoded customers
 */
public final class HardCodedCustomerLocationService implements CustomerLocationService {

    private static final Map<String, Customer> ALL_CUSTOMERS;

    @Override
    public Customer getCustomer(final String customerId) throws CustomerLocationException {
        Customer customer = ALL_CUSTOMERS.get(customerId);
        if (customer == null) {
            throw new CustomerLocationException();
        }
        return customer;
    }


    static {
        ALL_CUSTOMERS = new HashMap<>();
        ALL_CUSTOMERS.put("1", new Customer("1", "Mr Liverpool", "LIVERPOOL"));
        ALL_CUSTOMERS.put("2", new Customer("2", "Mrs London", "LONDON"));
        ALL_CUSTOMERS.put("3", new Customer("3", "Ms Leeds", "LEEDS"));
    }
}
