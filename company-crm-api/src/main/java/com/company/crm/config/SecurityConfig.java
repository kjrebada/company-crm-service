package com.company.crm.config;

import com.company.crm.security.AuthenticationFilter;
import com.company.crm.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * The default security configuration.
 *
 * There are two security configurations
 *
 * 1. Using default profile there will be no securities.
 * 2. Using Profiles=auth then there is a very basic web security.
 *    2a. You have to run the data.sql to add default users
 *    2b. You can use webuser:password or apiuser:password to get the access_token.
 *    2c. All our secure urls are under /secure/** endpoint, which one needs to be authenticated.
 *    2d. We will be using UserDetailsService provided by spring-security to check authentication.
 *
 * Password by the way is bcrypted.
 */
@Configuration
public class SecurityConfig {

    /**
     * Default security.
     *
     */
    @Profile("default")
    @Configuration
    @EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
    protected static class DefaultWebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable();
            http.antMatcher("/**").authorizeRequests().anyRequest().permitAll();
        }
    }

    /**
     * This one has the basic security setting.
     */
    @Profile("auth")
    @Configuration
    @EnableWebSecurity
    protected static class AuthWebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private AuthenticationFilter authenticationFilter;

        @Autowired
        private UserDetailsServiceImpl userDetailsService;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }

        private final RequestMatcher SECURED_URLS = new OrRequestMatcher(
                new AntPathRequestMatcher("/secure/**")
        );

        private final RequestMatcher PUBLIC_API_URLS = new NegatedRequestMatcher(SECURED_URLS);


        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }


        @Override
        public void configure(final WebSecurity web) {
            web.ignoring().requestMatchers(PUBLIC_API_URLS);
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http
                    .sessionManagement()
                    .sessionCreationPolicy(STATELESS)
                    .and()
                    .exceptionHandling()
                    .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), SECURED_URLS)
                    .and()
                    .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                    .logout().disable();
        }

        @Bean
        AuthenticationEntryPoint forbiddenEntryPoint() {
            return new HttpStatusEntryPoint(FORBIDDEN);
        }
    }

}
