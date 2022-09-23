package com.lab2.electronicQueue.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/login")
public class LoginController {
    @GetMapping()
    public String getLoginPage(){
        return "login";
    }
}
