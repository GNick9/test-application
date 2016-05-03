package com.sky.test.services;

import com.sky.test.model.Customer;

public interface CustomerLocationService {

    /**
     * retrieve a customer based on their customer id
     *
     * @param customerId the customer's id
     * @return a customer entity
     * @throws CustomerLocationException if the customer is not found
     */
    Customer getCustomer(String customerId) throws CustomerLocationException;

}
