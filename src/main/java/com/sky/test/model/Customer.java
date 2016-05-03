package com.sky.test.model;

/**
 * A dummy customer
 */
public class Customer {

    private String customerId;

    private String name;

    private String locationId;

    public Customer(String customerId, String name, String locationId) {
        this.customerId = customerId;
        this.name = name;
        this.locationId = locationId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getLocationId() {
        return locationId;
    }
}
