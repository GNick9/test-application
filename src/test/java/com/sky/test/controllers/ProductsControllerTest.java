package com.sky.test.controllers;

import com.sky.test.Application;
import com.sky.test.model.Customer;
import com.sky.test.model.Product;
import com.sky.test.services.CatalogueService;
import com.sky.test.services.CustomerLocationException;
import com.sky.test.services.CustomerLocationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebAppConfiguration
public class ProductsControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CatalogueService catalogueService;

    @Autowired
    private CustomerLocationService customerLocationService;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void index() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("customers", hasSize(4)));
    }

    @Test
    public void products() throws Exception {
        reset(catalogueService);
        reset(customerLocationService);
        Customer testCustomer = new Customer("1", "Name", "L1");
        when(customerLocationService.getCustomer("1")).thenReturn(
                testCustomer
        );
        when(customerLocationService.getCustomer("2")).thenThrow(
                new CustomerLocationException()
        );

        Product p1 = new Product("1", "Category", "Product 1", "L1");
        Product p2 = new Product("2", "Category", "Product 2", "L1");
        when(catalogueService.getProducts("L1")).thenReturn(
                Arrays.asList(p1, p2)
        );

        mockMvc.perform(get("/products").cookie(new Cookie("customerID", "1")))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attribute("products", allOf(
                        hasProperty("categories", hasItem("Category")),
                        hasProperty("products", allOf(hasItem(p1), hasItem(p2)))
                )));

        mockMvc.perform(get("/products").cookie(new Cookie("customerID", "2")))
                .andExpect(status().isOk())
                .andExpect(view().name("invalid"));

        verify(catalogueService, times(1)).getProducts("L1");
        verify(customerLocationService, times(1)).getCustomer("1");
        verify(customerLocationService, times(1)).getCustomer("2");
        verifyNoMoreInteractions(catalogueService);
        verifyNoMoreInteractions(customerLocationService);
    }

    @Test
    public void checkout() throws Exception {
        reset(catalogueService);
        reset(customerLocationService);
        Customer testCustomer = new Customer("1", "Name", "L1");
        when(customerLocationService.getCustomer("1")).thenReturn(
                testCustomer
        );
        when(customerLocationService.getCustomer("2")).thenThrow(
                new CustomerLocationException()
        );

        Product p1 = new Product("1", "Category", "Product 1", "L1");
        Product p2 = new Product("2", "Category", "Product 2", "L1");
        when(catalogueService.getProducts("L1")).thenReturn(
                Arrays.asList(p1, p2)
        );

        mockMvc.perform(post("/checkout").cookie(new Cookie("customerID", "1")).param("products", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("confirm"))
                .andExpect(model().attribute("customer", is(testCustomer)))
                .andExpect(model().attribute("products", allOf(hasItem(p1), hasItem(p2))));

        // post invalid product id
        mockMvc.perform(post("/checkout").cookie(new Cookie("customerID", "1")).param("products", "2", "3"))
                .andExpect(status().isForbidden());

        // invalid customer
        mockMvc.perform(post("/checkout").cookie(new Cookie("customerID", "2")).param("products", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("invalid"));

        // get should not map to a method
        mockMvc.perform(get("/checkout")).andExpect(status().is4xxClientError());

        verify(catalogueService, times(2)).getProducts("L1");
        verify(customerLocationService, times(2)).getCustomer("1");
        verify(customerLocationService, times(1)).getCustomer("2");
        verifyNoMoreInteractions(catalogueService);
        verifyNoMoreInteractions(customerLocationService);
    }
}