package com.lab1.FirstLab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainMenu(Model model) {
        model.addAttribute("title", "Main page");
        return "main_page";
    }

    @GetMapping("/student_1")
    public String ihorPage(Model model) {
        model.addAttribute("title", "Ihor page");
        return "student_pages/ihor_page";
    }

    @GetMapping("/student_2")
    public String hlibPage(Model model) {
        model.addAttribute("title", "Hlib page");
        return "student_pages/hlib_page";
    }

    @GetMapping("/student_3")
    public String ivanPage(Model model) {
        model.addAttribute("title", "Ivan page");
        return "student_pages/ivan_page";
    }
}
