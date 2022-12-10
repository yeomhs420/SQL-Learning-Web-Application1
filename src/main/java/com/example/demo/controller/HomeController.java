package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String home() {
        return "outputobj";
    }

}