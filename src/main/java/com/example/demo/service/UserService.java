package com.example.demo.service;

import com.example.demo.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

//    @Autowired UserRepository userRepository;
    @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

//    public String join(User user) {
//        dto.setUser_pw(bCryptPasswordEncoder.encode(user.getUser_pw()));
//
//        return userRepository.save(user.toEntity()).getUser_id();
//    }

    public void saveUserData() {
        List<User> UserList = null;

//        for(int i=0;i<UserList.size();i++) userRepository.save(UserList.get(i));
    }
}
