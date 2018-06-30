package com.company.crm.security;

import com.company.crm.model.Channel;
import com.company.crm.model.Client;
import com.company.crm.service.ClientService;
import com.company.crm.service.impl.UserDetailsServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.removeStart;

/**
 * This Filter will execute check if user have access to /secure APIs.
 *
 * What are checked?
 *
 * 1. We should have "Bearer {access-token}" in the header
 * 2. We should have "CHANNEL_NAME" in the header
 * 3. If both fields are in header, we need to check the current Client with the access-token and we need to verify
 *    if they have the rights to use the channel.
 * 4. If yes, then we will authenticate the user to the current security context.
 */
@Component
public final class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String accessToken = ofNullable(authorizationHeader)
                .map(value -> removeStart(value, "Bearer"))
                .map(String::trim).orElse("");

        String username = null;
        if (StringUtils.isNotBlank(accessToken)) {
            Client client = clientService.getClient(accessToken);
            if (null != client) {
                username = client.getClientName();
            }
        }

        if (StringUtils.isNotBlank(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            Channel authorizedChannel = userDetailsService.getChannelByUsername(username);
            String channelName = request.getHeader("CHANNEL_NAME");
            if (StringUtils.isNotBlank(channelName) && channelName.equalsIgnoreCase(authorizedChannel.name())) {
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(accessToken, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}
