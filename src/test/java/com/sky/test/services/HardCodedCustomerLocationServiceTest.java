package com.sky.test.services;

import com.sky.test.model.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HardCodedCustomerLocationServiceTest {

    CustomerLocationService customerLocationService;

    @Before
    public void setUp() {
        customerLocationService = new HardCodedCustomerLocationService();
    }

    @Test
    public void testGetCustomer() throws Exception {

        // expected customers
        Customer c1e =  new Customer("1", "Mr Liverpool", "LIVERPOOL");
        Customer c2e =  new Customer("2", "Mrs London", "LONDON");
        Customer c3e =  new Customer("3", "Ms Leeds", "LEEDS");

        // get the actual customers
        Customer c1a = customerLocationService.getCustomer("1");
        Customer c2a = customerLocationService.getCustomer("2");
        Customer c3a = customerLocationService.getCustomer("3");

        assertSame(c1e, c1a);
        assertSame(c2e, c2a);
        assertSame(c3e, c3a);
    }

    @Test(expected = CustomerLocationException.class)
    public void  testGetInvalidCustomer() throws Exception {
        customerLocationService.getCustomer("4");
    }

    private void assertSame(Customer c1e, Customer c1a) {
        Assert.assertEquals(c1e.getCustomerId(), c1a.getCustomerId());
        Assert.assertEquals(c1e.getLocationId(), c1a.getLocationId());
        Assert.assertEquals(c1e.getName(), c1a.getName());
    }
}
