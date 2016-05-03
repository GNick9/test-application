package com.sky.test.controllers;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sky.test.Application;
import com.sky.test.model.Customer;
import com.sky.test.model.Product;
import com.sky.test.services.CatalogueService;
import com.sky.test.services.CustomerLocationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.Mockito.*;

/**
 * TODO Some warnings etc to sort out for the web client when getting jquery and bootstrap themes
 * but the test still runs.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductsControllerWebTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CatalogueService catalogueService;

    @Autowired
    private CustomerLocationService customerLocationService;

    @Test
    public void webTest() throws Exception {

        Customer testCustomer = new Customer("1", "Name", "L1");
        when(customerLocationService.getCustomer("1")).thenReturn(
                testCustomer
        );

        Product p1 = new Product("1", "Category", "Product 1", "L1");
        Product p2 = new Product("2", "Category", "Product 2", "L1");
        Product p3 = new Product("3", "Category", "Product 3", "L1");
        when(catalogueService.getProducts("L1")).thenReturn(
                Arrays.asList(p1, p2, p3)
        );

        try (WebClient webClient = new WebClient()) {

            HtmlPage index = webClient.getPage("http://localhost:8080");
            DomElement e = index.getElementById("customer1");

            HtmlPage products = e.click();

            DomElement prod1 = products.getElementById("product1");
            DomElement prod2 = products.getElementById("product2");
            DomElement prod3 = products.getElementById("product3");

            // check the basket updates as products are selected/de-selected
            DomElement basket = products.getElementById("basket");
            Assert.assertEquals(0, basket.getChildElementCount());
            prod1.click();
            Assert.assertEquals(1, basket.getChildElementCount());
            prod2.click();
            Assert.assertEquals(2, basket.getChildElementCount());
            prod3.click();
            Assert.assertEquals(3, basket.getChildElementCount());
            prod3.click();
            Assert.assertEquals(2, basket.getChildElementCount());

            HtmlPage confirmation = products.getElementById("checkout").click();

            DomNodeList<DomElement> nodes = confirmation.getElementsByTagName("li");

            Assert.assertEquals(2, nodes.size());
            Assert.assertEquals("Product 1", nodes.get(0).getTextContent());
            Assert.assertEquals("Product 2", nodes.get(1).getTextContent());
        }

    }
}