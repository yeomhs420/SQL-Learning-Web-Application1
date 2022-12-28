package com.example.demo.service;

import com.example.demo.entity.user.User;
import com.example.demo.jpa.repository.user.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("userTransactionManager")
public class EagerService {

    @Autowired
    UserRepository userRepository;

    public User getUserWithEagerProgress(String userId) {
        User user = userRepository.findByUserId(userId).get(0);
        Hibernate.initialize(user.getProgress());
        return user;
    }
}
