package com.company.crm;

import com.company.crm.model.Customer;

import java.util.List;

/**
 * {@link CrmService}
 */
public interface CrmService {

    /**
     * Add a new customer or update an existing customer
     *
     * @param customer the {@link Customer}
     */
    void updateCustomer(Customer customer);

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
     * @
     */
    Customer getCustomerById(long id);

    /**
     * Retrieve all customers
     *
     * @return list of customers
     */
    List<Customer> getAllCustomers();
}
