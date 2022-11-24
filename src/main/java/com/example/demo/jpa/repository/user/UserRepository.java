package com.example.demo.jpa.repository.user;

import com.example.demo.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
//    List<User> findAll();
}
