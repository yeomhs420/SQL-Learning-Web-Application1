package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping("login")
    public String login() {
        return "login/loginpage";
    }

    @GetMapping("signup")
    public String signup() {
        return "signup/signuppage";
    }

}
