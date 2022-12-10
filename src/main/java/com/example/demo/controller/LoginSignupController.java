package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginSignupController {

    @GetMapping("login")
    public String login() {
        return "login/loginpage";
    }

    @GetMapping("signup")
    public String signup() {
        return "signup/signuppage";
    }

}
