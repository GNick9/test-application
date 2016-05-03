package com.sky.test.model;

/**
 * A simplified Product where no attempt has been made to normalise the category or location properties
 * Provides a category and description of a product on offer at a particular location (or all locations if locationId is blank)
 */
public class Product {

    private String productId;

    private String category;

    private String description;

    private String locationId;

    /**
     * Create a product
     *
     * @param productId   unique id of the product
     * @param category    the product category
     * @param description the product description
     * @param locationId  the product location id
     */
    public Product(String productId, String category, String description, String locationId) {
        this.productId = productId;
        this.category = category;
        this.description = description;
        this.locationId = locationId;
    }


    public String getProductId() {
        return productId;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getLocationId() {
        return locationId;
    }
}
