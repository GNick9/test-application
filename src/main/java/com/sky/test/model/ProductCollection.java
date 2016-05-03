package com.sky.test.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A helper class to provide the unique categories of the products
 */
public class ProductCollection {

    private List<Product> products;

    private List<String> categories;

    public ProductCollection(final List<Product> products) {
        this.products = products;
        categories = new ArrayList<>();
        for (Product product : products) {
            if (!categories.contains(product.getCategory())){
                categories.add(product.getCategory());
            }
        }
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<Product> getProducts(final String category) {
        return products.stream().filter(p->p.getCategory().equals(category)).collect(Collectors.toList());
    }

    public List<Product> getProducts() {
        return products;
    }
}