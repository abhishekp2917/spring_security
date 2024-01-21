package com.example2.controller;

import com.example2.model.Customer;
import com.example2.repository.CustomerRepository;
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

    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser(@RequestBody Customer customer) {
        ResponseEntity<String> response = null;
        try{
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            customer = customerRepository.save(customer);
            if(customer.getId()>0) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Customer registered successfully");
            }
        }
        catch (Exception e) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Error occured : %s", e.getMessage()));
        }
        return response;
    }
}
