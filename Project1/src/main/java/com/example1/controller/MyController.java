/*
    this controller has mapping to various pages
*/

package com.example1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class MyController {

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
}
