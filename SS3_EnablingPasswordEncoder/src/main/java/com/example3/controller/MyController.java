/*
    this controller has mapping to various pages
*/

package com.example3.controller;

import com.example3.model.Customer;
import com.example3.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class MyController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String getHome() {

        return "Welcome to Home Page";
    }

    @GetMapping("/myAccount")
    public String getMyAccount() {

        return "Welcome to your Account Page";
    }

    @GetMapping("/myLoans")
    public String getMyLoans() {

        return "Welcome to your Loans Page";
    }

    @GetMapping("/myCards")
    public String getMyCards() {

        return "Welcome to your Cards Page";
    }

    @GetMapping("/notices")
    public String getNotices() {

        return "Welcome to Notice Page";
    }

    @GetMapping("/contact")
    public String getContact() {

        return "Welcome to Contact Page";
    }

    // mapping for creating new customer(or user) record
    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser(@RequestBody Customer customer) {
        ResponseEntity<String> response = null;
        try{
            // encoding password using password encoder before saving the password in database because we have enabled password
            // encoder in spring security configuration
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            // calling save() method of CustomerRepository to save the Customer details in customer table
            customer = customerRepository.save(customer);
            // if record got saved, then we will get customer id returned
            // in that case return the response with HttpStatus as CREATED
            if(customer.getId()>0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Customer registered successfully");
            }
        }
        // in case of any exception return HttpStatus as INTERNAL_SERVER_ERROR
        catch (Exception e) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Error occured : %s", e.getMessage()));
        }
        return response;
    }
}
