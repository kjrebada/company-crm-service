package com.company.crm.service;

import com.company.crm.model.Client;

/**
 * {@link ClientService}
 */
public interface ClientService {

    /**
     * Get the client by id
     *
     * @param clientId
     * @return
     */
    Client getClient(String clientId);

    /**
     * Save the client
     *
     * @param client
     */
    void saveClient(Client client);

    /**
     * Get the client by name
     *
     * @param clientName
     * @return
     */
    Client getClientByClientName(String clientName);

    /**
     * Delete the client entry by client id
     *
     * @param clientId
     */
    void deleteClientByClientId(String clientId);
}
