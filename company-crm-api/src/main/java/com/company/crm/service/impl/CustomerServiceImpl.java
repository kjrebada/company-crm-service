package com.company.crm.service.impl;

import com.company.crm.CrmService;
import com.company.crm.model.CustomerDTO;
import com.company.crm.model.Customer;
import com.company.crm.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Business logic layer for Customer profile.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CrmService crmService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * @inheritDoc
     */
    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        crmService.updateCustomer(customer);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteCustomerById(long id) {
        crmService.deleteCustomerById(id);
    }

    /**
     * @inheritDoc
     */
    @Override
    public CustomerDTO getCustomerById(long id) {
        Customer customer = crmService.getCustomerById(id);
        CustomerDTO customerDTO = convertToDTO(customer);
        return customerDTO;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = crmService.getAllCustomers();

        return customers.stream()
                .map(customer -> convertToDTO(customer))
                .collect(Collectors.toList());
    }

    /**
     * Convert entity to dto
     *
     * @param customer
     * @return
     */
    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
        return customerDTO;
    }

    /**
     * Convert dto to entity
     *
     * @param customerDTO
     * @return
     */
    private Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        return customer;
    }
}
