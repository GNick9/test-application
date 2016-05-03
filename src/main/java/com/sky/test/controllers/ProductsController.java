package com.sky.test.controllers;

import com.sky.test.model.Customer;
import com.sky.test.model.Product;
import com.sky.test.model.ProductCollection;
import com.sky.test.services.CatalogueService;
import com.sky.test.services.CustomerLocationException;
import com.sky.test.services.CustomerLocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductsController {

    private Logger logger = LoggerFactory.getLogger(ProductsController.class);

    private final CustomerLocationService customerLocationService;

    private final CatalogueService catalogueService;

    @Autowired
    public ProductsController(CustomerLocationService customerLocationService, CatalogueService catalogueService) {
        this.customerLocationService = customerLocationService;
        this.catalogueService = catalogueService;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(final Model model) {

        logger.debug("Entering index");

        // add some demo customers to save on the amount of markup on the test landing page
        List<Customer> demoCustomers = new ArrayList<>();
        demoCustomers.add(new Customer("1", "Mr Liverpool", "Liverpool"));
        demoCustomers.add(new Customer("2", "Mrs London", "London"));
        demoCustomers.add(new Customer("3", "Ms Leeds", "Leeds"));
        demoCustomers.add(new Customer("4", null, "Invalid"));
        model.addAttribute("customers", demoCustomers);
        return "index";
    }


    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public String products(@CookieValue(name = "customerID", defaultValue = "") final String customerId, final Model model) {

        logger.debug("Entering products");

        try {
            Customer customer = customerLocationService.getCustomer(customerId);
            model.addAttribute("customer", customer);
            model.addAttribute("products", new ProductCollection(catalogueService.getProducts(customer.getLocationId())));
            return "products";
        } catch (CustomerLocationException e) {
            logger.error("Unable to locate customer " + customerId);
            return "invalid";
        }
    }

    @RequestMapping(path = "/checkout", method = RequestMethod.POST)
    public String checkout(@CookieValue(name = "customerID", defaultValue = "") final String customerId,
                           @RequestParam("products") final String[] products, final Model model) throws InvalidProductCodeException {

        // TODO ensure we are not vulnerable to CSRF attacks

        logger.debug("Entering checkout");

        // validate the products passed
        try {
            Customer customer = customerLocationService.getCustomer(customerId);
            List<Product> chosenProducts = new ArrayList<>(products.length);
            List<Product> productList = catalogueService.getProducts(customer.getLocationId());
            Map<String, Product> validProductMap = new HashMap<>(productList.size());
            for (Product product : productList) {
                validProductMap.put(product.getProductId(), product);
            }
            for (String product : products) {
                if (!validProductMap.containsKey(product)) {
                    logger.error("Invalid products posted for customer " + customerId);
                    throw new InvalidProductCodeException();
                } else {
                    chosenProducts.add(validProductMap.get(product));
                }
            }

            model.addAttribute("customer", customer);
            model.addAttribute("products", chosenProducts);
            return "confirm";

        } catch (CustomerLocationException e) {
            logger.error("Unable to locate customer " + customerId);
            return "invalid";
        }
    }
}
