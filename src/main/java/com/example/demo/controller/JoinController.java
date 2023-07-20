package com.example.demo.controller;

import com.example.demo.entity.user.User;
import com.example.demo.vo.UserDto;
import com.example.demo.jpa.repository.user.UserRepository;
import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/join")
public class JoinController {

    @Autowired
    private HttpSession session;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserRepository userRepository;


    @RequestMapping({"/", ""})
    public String Join(HttpServletRequest request, Model model){
        Map<String, ?> flashMap  = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null){
            model.addAttribute("id_error", flashMap.get("id_error"));
            model.addAttribute("password_error", flashMap.get("password_error"));
            model.addAttribute("email_error", flashMap.get("email_error"));
        }

        return "/login/join";
    }

    @PostMapping("/action")
    public String JoinAction(@Valid UserDto userDto, BindingResult bindingResult, RedirectAttributes re, Model model) {

        if(bindingResult.hasErrors()){
            if(!bindingResult.getFieldErrors("userID").isEmpty())
                re.addFlashAttribute("id_error", bindingResult.getFieldErrors("userID"));
            if(!bindingResult.getFieldErrors("userPassword").isEmpty())
                re.addFlashAttribute("password_error", bindingResult.getFieldErrors("userPassword"));
            if(!bindingResult.getFieldErrors("userEmail").isEmpty())
                re.addFlashAttribute("email_error", bindingResult.getFieldErrors("userEmail"));

            return "redirect:/join";
        }

        if(loginService.create(userDto, model) == true) {
            return "login/join";
        }

        User user = userRepository.findByUserID(userDto.getUserID()).get(0);

        session.setAttribute("user", user);

        session.setMaxInactiveInterval(7200);

        return "redirect:/home";

    }

}
