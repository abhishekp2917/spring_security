/*
    configuring spring security based on our requirements
*/

package com.example3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                // disabling CSRF filter which will allow user to make post request without any CSRF checking
                // however, it's not recommended to entirely disable csrf protection as it makes application prone to security attacks
                .csrf().disable()
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

    // creating bean of PasswordEncoder
    // here we are specifying spring security to use BCrypt password encoder in auth process
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

}
