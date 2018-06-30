package com.company.crm;

import com.company.crm.impl.CrmServiceImpl;
import com.company.crm.model.Customer;
import com.company.crm.repo.CustomerRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CrmServiceImplTest {

    @InjectMocks
    private CrmServiceImpl crmService;

    @Mock
    private CustomerRepo customerRepo;

    @Test
    public void testGetCustomerById() {
        when(customerRepo.findById(anyLong())).thenReturn(getCustomer());
        assertNotNull(crmService.getCustomerById(1L));
    }

    @Test
    public void testGetAllCustomers() {
        when(customerRepo.findAll()).thenReturn(Arrays.asList(getCustomer().get()));
        List<Customer> customers = crmService.getAllCustomers();
        assertNotNull(customers);
        assertEquals(1, customers.size());
    }

    @Test
    public void testAddCustomer() {
        when(customerRepo.saveAndFlush(any(Customer.class))).thenReturn(getCustomer().get());
        crmService.updateCustomer(getCustomer().get());
    }

    @Test
    public void testDeleteCustomer() {
        doNothing().when(customerRepo).deleteById(anyLong());
        crmService.deleteCustomerById(1L);
    }

    private Optional<Customer> getCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("First");
        customer.setLastName("Last");
        return Optional.of(customer);
    }

}
