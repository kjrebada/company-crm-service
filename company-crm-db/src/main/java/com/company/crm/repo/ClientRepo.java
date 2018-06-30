package com.company.crm.repo;

import com.company.crm.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends JpaRepository<Client, String> {

    @Query("select a from Client a where a.clientName = :clientName")
    Client findByClientName(@Param("clientName")String clientName);
}
