package com.company.crm.service.impl;

import com.company.crm.model.Client;
import com.company.crm.repo.ClientRepo;
import com.company.crm.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Business logic layer for Client profile.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepo clientRepo;

    /**
     * @inheritDoc
     */
    @Override
    public Client getClient(String clientId) {
        return clientRepo.findById(clientId).orElse(null);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void saveClient(Client client) {
        clientRepo.saveAndFlush(client);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Client getClientByClientName(String clientName) {
        return clientRepo.findByClientName(clientName);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteClientByClientId(String clientId) {
        clientRepo.deleteById(clientId);
    }
}
