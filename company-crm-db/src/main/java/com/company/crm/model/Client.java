package com.company.crm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CLIENT")
public class Client {

    @Id
    @Column(name = "CLIENT_ID", unique = true)
    @NotNull
    private String clientId;

    @Column(name = "CLIENT_NAME")
    @NotNull
    @NotEmpty
    private String clientName;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}