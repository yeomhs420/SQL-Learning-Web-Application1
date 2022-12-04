package com.example.demo.controller;

import com.example.demo.entity.user.User;
import com.example.demo.entity.user.UserDto;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private HttpSession session;

    @Autowired
    private LoginService loginService;

    @RequestMapping({"/", ""})
    public String Login(){ return "/login/login"; };

    @PostMapping("/action")
    public String LoginAction(UserDto userDto, Model model){

        if(loginService.isUser(userDto.getUserID(), userDto.getUserPassword(), model) == false)
            return "login/login";

        User user = User.createUser(userDto);

        session.setAttribute("user", user);

        return "redirect:/home";

    }

    @RequestMapping("/logout")
    public String LoginAction(){
        session.invalidate();

        return "redirect:/home";
    }

}
