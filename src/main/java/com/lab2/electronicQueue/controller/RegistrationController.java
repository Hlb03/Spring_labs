package com.lab2.electronicQueue.controller;

import com.lab2.electronicQueue.entity.User;
import com.lab2.electronicQueue.service.serviceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {


    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String toRegistration(Model model){
        model.addAttribute("user", new User());

        return "registration";
    }

    @PostMapping
    public String registration(@ModelAttribute @Valid User user, Errors errors, Model model,
                               @RequestParam ("secondPassword")  String secondPassword) {
        if (errors.hasErrors())
            return "registration";

        try {
            userService.addUser(user, secondPassword);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "registration";
        }

        return "redirect:/login";
    }

}
