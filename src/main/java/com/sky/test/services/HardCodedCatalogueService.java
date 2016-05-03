package com.sky.test.services;

import com.sky.test.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A hard coded catalogue service to provide a pre-defined list of products
 */
public final class HardCodedCatalogueService implements CatalogueService {

    /**
     * A list of all products
     */
    private static final List<Product> ALL_PRODUCTS;

    @Override
    public List<Product> getProducts(final String locationId) {

        // get any product without a specific location or a location that matches the provided location
        return ALL_PRODUCTS.stream().filter(s -> s.getLocationId() == null || s.getLocationId().equals(locationId))
                .collect(Collectors.toList());
    }

    /**
     * Initialise our hard coded list of products
     */
    static {
        ALL_PRODUCTS = new ArrayList<>();
        ALL_PRODUCTS.add(new Product("1", "Sports", "Arsenal TV", "LONDON"));
        ALL_PRODUCTS.add(new Product("2", "Sports", "Chelsea TV", "LONDON"));
        ALL_PRODUCTS.add(new Product("3", "Sports", "Liverpool TV", "LIVERPOOL"));
        ALL_PRODUCTS.add(new Product("4", "News", "Sky News", null));
        ALL_PRODUCTS.add(new Product("5", "News", "Sky Sports News", null));
    }
}
