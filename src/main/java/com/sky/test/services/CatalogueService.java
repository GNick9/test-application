package com.sky.test.services;

import com.sky.test.model.Product;

import java.util.List;

public interface CatalogueService {

    /**
     * For a given location return a list of products available.
     * Products with no location specified should always be returned.
     *
     * @param locationId the location to restrict the available products
     * @return a list of the products available
     */
    List<Product> getProducts(final String locationId);
}
