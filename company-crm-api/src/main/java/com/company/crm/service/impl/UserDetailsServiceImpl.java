package com.company.crm.service.impl;

import com.company.crm.model.Channel;
import com.company.crm.model.User;
import com.company.crm.repo.UserRepo;
import com.company.crm.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Business logic layer for User details.
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return new SecurityUser(
                    user.getId(),
                    user.getUsername(),
                    user.getFirstname(),
                    user.getLastname(),
                    user.getPassword(),
                    user.getChannel(),
                    new ArrayList<>(),
                    true);
        }
    }

    /**
     * Return the channel authorize for the given username
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    public Channel getChannelByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return user.getChannel();
        }
    }
}
