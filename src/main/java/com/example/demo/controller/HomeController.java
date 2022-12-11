package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    HttpSession session;

    @GetMapping
    public String home(Model model) {
        //model.addAttribute("userID", session.getAttribute("userID"));
        return "outputobj";
    }
}