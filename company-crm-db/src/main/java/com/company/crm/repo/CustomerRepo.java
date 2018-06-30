package com.company.crm.repo;

import com.company.crm.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Customer profile Repository
 */
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
