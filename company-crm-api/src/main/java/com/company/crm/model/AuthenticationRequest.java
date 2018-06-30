package com.company.crm.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * This will hold our username and password in request
 */
@Data
@Getter
@Setter
public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String username;

    @NotNull
    private String password;
}
