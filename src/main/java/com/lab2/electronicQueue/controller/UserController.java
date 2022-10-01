package com.lab2.electronicQueue.controller;

import com.lab2.electronicQueue.DTO.UserDTO;
import com.lab2.electronicQueue.entity.Queue;
import com.lab2.electronicQueue.entity.User;
import com.lab2.electronicQueue.service.serviceImpl.QueueService;
import com.lab2.electronicQueue.service.serviceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final QueueService queueService;

    @Autowired
    public UserController(UserService userService, QueueService queueService) {
        this.userService = userService;
        this.queueService = queueService;
    }

    @GetMapping
    public String getUserPage(Model model, @AuthenticationPrincipal User user){
        UserDTO selectedUser = userService.userToUserDTO(user);
        model.addAttribute("selectedUser",selectedUser);
        if(user.getUserRole().name().equals("USER")){
            return "userPersonalOffice";
        }
        return "adminPersonalOffice";
    }

    @GetMapping("/add/queue")
    public String getAddQueuePage(Model model){
        model.addAttribute("queue", new Queue());
        return "addQueue";
    }

    @PostMapping("/add/queue")
    public String addQueue(@Valid @ModelAttribute Queue queue, Errors errors){
        if(errors.hasErrors()){
            return "addQueue";
        }
        queueService.addQueue(queue);
        return "redirect:/user";
    }
}
