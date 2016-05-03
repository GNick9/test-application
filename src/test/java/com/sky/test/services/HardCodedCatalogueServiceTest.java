package com.sky.test.services;

import com.sky.test.model.Product;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HardCodedCatalogueServiceTest {
    @Test
    public void testGetProducts() throws Exception {

        CatalogueService catalogueService = new HardCodedCatalogueService();

        // expected products
        Product arsenal = new Product("1", "Sports", "Arsenal TV", "LONDON");
        Product chelsea = new Product("2", "Sports", "Chelsea TV", "LONDON");
        Product liverpool = new Product("3", "Sports", "Liverpool TV", "LIVERPOOL");
        Product skyNews = new Product("4", "News", "Sky News", null);
        Product skySportsNews = new Product("5", "News", "Sky Sports News", null);

        // get product for no location
        List<Product> products = catalogueService.getProducts(null);

        // expect 2 products
        Assert.assertNotNull(products);
        Assert.assertEquals(2, products.size());

        // ensure they are in a predictable order
        sortProducts(products);

        assertSame(products.get(0), skyNews);
        assertSame(products.get(1), skySportsNews);

        // now for LONDON
        products = catalogueService.getProducts("LONDON");

        // expect 4 products
        Assert.assertNotNull(products);
        Assert.assertEquals(4, products.size());

        // ensure they are in a predictable order
        sortProducts(products);

        assertSame(products.get(0), arsenal);
        assertSame(products.get(1), chelsea);
        assertSame(products.get(2), skyNews);
        assertSame(products.get(3), skySportsNews);

        // now for LIVERPOOL
        products = catalogueService.getProducts("LIVERPOOL");

        // expect 3 products
        Assert.assertNotNull(products);
        Assert.assertEquals(3, products.size());

        // ensure they are in a predictable order
        sortProducts(products);

        assertSame(products.get(0), liverpool);
        assertSame(products.get(1), skyNews);
        assertSame(products.get(2), skySportsNews);
    }

    /**
     * Sort the products into alphabetical order by description
     *
     * @param products the list of products
     */
    private void sortProducts(List<Product> products) {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getDescription().compareTo(o2.getDescription());
            }
        });
    }

    /**
     * Assert that two products are the same
     *
     * @param actual   the actual product
     * @param expected the expected product
     */
    private void assertSame(Product actual, Product expected) {
        Assert.assertEquals(actual.getProductId(), expected.getProductId());
        Assert.assertEquals(actual.getCategory(), expected.getCategory());
        Assert.assertEquals(actual.getDescription(), expected.getDescription());
        Assert.assertEquals(actual.getLocationId(), expected.getLocationId());
    }
}
