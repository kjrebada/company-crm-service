package com.company.crm.service;

import com.company.crm.model.CustomerDTO;

import java.util.List;

/**
 * {@link CustomerService}
 */
public interface CustomerService {

    /**
     * Add a new customer or update an existing customer
     *
     * @param customerDTO the {@link CustomerDTO}
     */
    void updateCustomer(CustomerDTO customerDTO);

    /**
     * Delete a customer by id
     *
     * @param id the customer id
     */
    void deleteCustomerById(long id);

    /**
     * Retrieve a customer by id
     *
     * @param id the customer id
     * @return the customer
     */
    CustomerDTO getCustomerById(long id);

    /**
     * Retrieve all customers
     *
     * @return list of customers
     */
    List<CustomerDTO> getAllCustomers();

}
