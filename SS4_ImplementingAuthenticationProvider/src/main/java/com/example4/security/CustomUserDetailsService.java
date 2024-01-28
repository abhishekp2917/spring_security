/*
    providing implementation of UserDetailsService interface which is being used by AuthenticationProvider to fetch the
    user details such as username, password etc from the storage system to compare with the user details
    provided by user while logging in

    after implementing this we would be able to store the user details in DB or any other storage system and can use those
    user details to log into application unlike earlier we have to pass 'user' as username and password as what was generated
    when we first started the application
*/

package com.example4.security;

import com.example4.model.Customer;
import com.example4.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // CustomerRepository object is to interact with customer(or user) table of the DB
    @Autowired
    private CustomerRepository userRepository;

    // loadUserByUsername fetches the User by the username from the storage system and returns the details by encapsulating
    // it in UserDetails class
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // fetching customer details from the customer table whose username matches with the provided username
        Customer customer = userRepository.getCustomerByUsername(username);
        if(customer!=null) {
            // if user exists, create User object which has username, password and authority details in it and return the
            // object
            // SimpleGrantedAuthority being implementation of GrantedAuthority, we passed the list of GrantedAuthority to User
            // object. This list will have all the authority that this particular user has
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(customer.getAuthority()));
            return new User(customer.getUsername(), customer.getPassword(), authorities);
        }
        // if user doesn't exists throw 'UsernameNotFoundException' exception
        else throw new UsernameNotFoundException(String.format("User by username %s not found", username));
    }
}
