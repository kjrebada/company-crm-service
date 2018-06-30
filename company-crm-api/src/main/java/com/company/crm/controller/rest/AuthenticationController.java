package com.company.crm.controller.rest;

import com.company.crm.model.Client;
import com.company.crm.model.AuthenticationRequest;
import com.company.crm.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

/**
 * Controller for the authentication.
 */
@Api(tags = "REST API for Users to access the system")
@Profile("auth")
@RestController
@RequestMapping("/rest")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManagerBean;

    @Autowired
    private ClientService clientService;

    /**
     * Login the user given the username and password.
     *
     * In real-world we will use jdbctokenstore's oauth_access_token to save the access_token.
     * To make it simpler, we will just use UsernamePasswordAuthenticationToken to login,
     * authenticate it, then we save the generated access_token to a client table.
     *
     * @param authenticationRequest the {@link AuthenticationRequest}
     * @return access_token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody final AuthenticationRequest authenticationRequest) {
        final String username = authenticationRequest.getUsername();
        final String password = authenticationRequest.getPassword();

        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        String accessToken = UUID.randomUUID().toString();

        try {
            if (StringUtils.isBlank(accessToken)) {
                throw new BadCredentialsException("Unauthorized access");
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username, password, new ArrayList<>());
            authenticationManagerBean.authenticate(authentication);

            Client client = new Client();
            client.setClientId(accessToken);
            client.setClientName(username);
            clientService.saveClient(client);

        } catch (BadCredentialsException | DisabledException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }

        return ResponseEntity.ok(accessToken);
    }

    /**
     * Logout from the system
     *
     * @param authenticationRequest {@link AuthenticationRequest}
     * @return
     */
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization",
                            value = "Bearer {access-token}",
                            required = true,
                            dataType = "string",
                            paramType = "header"),
                    @ApiImplicitParam(name = "CHANNEL_NAME",
                            value = "authorized channel for user",
                            required = true,
                            dataType = "string",
                            paramType = "header")
            }
    )
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody final AuthenticationRequest authenticationRequest) {
        final String username = authenticationRequest.getUsername();
        final String password = authenticationRequest.getPassword();

        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username, password, new ArrayList<>());
            authenticationManagerBean.authenticate(authentication);

            Client client = clientService.getClientByClientName(username);
            Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();

            if (null != client
                    && null != currentAuthentication
                    && client.getClientId().equalsIgnoreCase(currentAuthentication.getPrincipal().toString())) {
                SecurityContextHolder.clearContext();
                clientService.deleteClientByClientId(client.getClientId());
            } else {
                throw new BadCredentialsException("Unauthorized logout");
            }

        } catch (BadCredentialsException | DisabledException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }

        return ResponseEntity.ok("logout successful");
    }
}
