package com.company.crm.service;

import com.company.crm.CrmService;
import com.company.crm.model.Customer;
import com.company.crm.model.CustomerDTO;
import com.company.crm.service.impl.CustomerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CrmService crmService;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testUpdateCustomer() {
        doNothing().when(crmService).updateCustomer(any(Customer.class));
        customerService.updateCustomer(new CustomerDTO());

        verify(crmService, times(1)).updateCustomer(any(Customer.class));
    }

    @Test
    public void testDeleteCustomer() {
        doNothing().when(crmService).deleteCustomerById(anyLong());
        customerService.deleteCustomerById(1L);

        verify(crmService, times(1)).deleteCustomerById(anyLong());
    }

    @Test
    public void testGetCustomer() {
        when(crmService.getCustomerById(anyLong())).thenReturn(getCustomer());
        when(modelMapper.map(any(), any())).thenReturn(getCustomerDTOFromCustomerEntity());
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        assertNotNull(customerDTO);
        assertEquals(Long.parseLong("1"), Long.parseLong(customerDTO.getId().toString()));
    }

    @Test
    public void testAllGetCustomers() {
        when(crmService.getAllCustomers()).thenReturn(Arrays.asList(getCustomer()));
        when(modelMapper.map(any(), any())).thenReturn(getCustomerDTOFromCustomerEntity());
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        assertNotNull(customerDTOS);
        assertEquals(1, customerDTOS.size());
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("first");
        customer.setLastName("last");
        customer.setDateOfBirth(new Date());
        return customer;
    }

    private CustomerDTO getCustomerDTOFromCustomerEntity() {
        ModelMapper modelMapper = new ModelMapper();
        CustomerDTO customerDTO = modelMapper.map(getCustomer(), CustomerDTO.class);
        return customerDTO;
    }
}
