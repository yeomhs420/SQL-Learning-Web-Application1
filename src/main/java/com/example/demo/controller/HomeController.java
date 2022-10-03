package com.example.demo.controller;

import com.example.demo.service.test.SQLResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired SQLResultService sqlResultService;

    @GetMapping("/home")
    public String home() {
        return "outputobj";
    }
}