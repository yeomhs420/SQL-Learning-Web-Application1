package com.example.demo.service;

import com.example.demo.entity.user.User;
import com.example.demo.entity.user.UserDto;
import com.example.demo.jpa.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    HttpSession session;
    @Autowired
    UserRepository userRepository;

    public void encryptPassword(User user){
        String enPw = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(enPw);

        userRepository.save(user);
    }

    public boolean create(UserDto userDto, Model model) { // 회원가입

        User user = User.createUser(userDto);  // @valid 를 거친 userDto 객체를 암호화를 위해 user 객체로 형변환

        if(user.getUserID() == "" || user.getUserPassword() == "" || user.getUserName() == "" || user.getUserEmail() == "") {
            model.addAttribute("msg", "입력이 안된 사항이 있습니다.");
            return true;
        }

        else if(userRepository.findByUserId(user.getUserID()).isEmpty()){

            try{
                List<Boolean> progress = new ArrayList<>();
                for(int i=0;i<17;i++) progress.add(false);
                user.setProgress(progress);
                encryptPassword(user);
            }catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }

        else{
            model.addAttribute("msg", "이미 존재하는 ID입니다.");
            return true;
        }
    }

    public boolean isUser(String id, String pw, Model model){   // 로그인 본인 확인 절차

        if(userRepository.findByUserId(id).isEmpty() == true){
            model.addAttribute("msg", "존재하지 않는 ID입니다.");
            return false;
        }

        else{
            if(passwordEncoder.matches(pw, userRepository.findByUserId(id).get(0).getUserPassword()))
                return true;

            model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
            return false;
        }
    }

    public User getUser(){
        return (User) session.getAttribute("user");
    }
}