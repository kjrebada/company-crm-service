package com.company.crm.impl;

import com.company.crm.CrmService;
import com.company.crm.model.Customer;
import com.company.crm.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Normally, this is where we talk to CRM gateway.
 * We use RestTemplate to do REST call to any exposed CRM gateway API.
 *
 * To simulate the other end of it,
 * we will just interface to the database directly.
 */
@Service
@Transactional
public class CrmServiceImpl implements CrmService {
    @Autowired
    private CustomerRepo customerRepo;

    /**
     * @inheritDoc
     */
    @Override
    public void updateCustomer(Customer customer) {
        customerRepo.saveAndFlush(customer);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteCustomerById(long id) {
        customerRepo.deleteById(id);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Customer getCustomerById(long id) {
        Optional<Customer> customer = customerRepo.findById(id);
        return customer.orElse(null);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        return customers;
    }
}
