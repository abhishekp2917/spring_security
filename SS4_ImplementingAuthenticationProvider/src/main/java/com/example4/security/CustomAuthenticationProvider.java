/*
    providing implementation of AuthenticationProvider and defining it as a Component so spring security will create bean
    of it and will use this CustomAuthenticationProvider for authentication purpose
*/

package com.example4.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // extracting username and password entered by the user
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        // fetching userDetails if exists by passing the username
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        // if user exists then we will proceed to check whether password matches with what it's stored in db
        if(userDetails!=null) {
            // since we have configured passwordEncoder in spring security, we will use passwordEncoder to verify the password
            if(passwordEncoder.matches(password, userDetails.getPassword())) {
                // if authentication is successful, return UsernamePasswordAuthenticationToken which has username, password and it's authorities
                return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
            }
            // if password doesn't match, throw exception
            else throw new BadCredentialsException("Invalid password");
        }
        // if username doesn't exist, throw exception
        else throw new BadCredentialsException("Invalid username");
    }

    // overriding supports method
    // whenever AuthenticationManager gets an Authentication token, it will iterate over all the configured AuthenticationProvider
    // and will call this method to check if any particular AuthProvider supports the provided Auth Token.
    @Override
    public boolean supports(Class<?> authentication) {
        // here we are defining that this AuthProvider supports UsernamePasswordAuthenticationToken type Authentication
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
