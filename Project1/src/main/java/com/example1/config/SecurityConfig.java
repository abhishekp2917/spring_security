package com.example1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // spring security uses SecurityFilterChain to apply the filters
    // here we are configuring SecurityFilterChain based on our requirements
    @Bean
    SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {

        http
                // begins configuration for request authorization.
                .authorizeHttpRequests()
                // here we are defining the paths that needs to be authenticated
                .requestMatchers("/myAccount", "/myCards", "/myLoans").authenticated()
                // making the paths mentioned as publicly accessible
                .requestMatchers("/contact", "/notices", "/register").permitAll()
                .and()
                // defining what type of security filter we want to add
                // adding formLogin filter will configure form-based login. This means that users will be redirected to a login page when authentication is required.
                // credentials are submitted as part of the form submission, usually via a POST request.
                .formLogin()
                .and()
                // configuring HTTP Basic authentication filter wherein credentials are sent in the request headers with each request.
                // The credentials are base64-encoded but not encrypted.
                .httpBasic();
        return http.build();
    }
}
