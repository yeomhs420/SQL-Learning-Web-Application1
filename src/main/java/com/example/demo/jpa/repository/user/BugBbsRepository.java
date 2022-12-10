package com.example.demo.jpa.repository.user;
import com.example.demo.entity.user.BugBbs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BugBbsRepository extends JpaRepository<BugBbs, Long> {

    @Override
    List<BugBbs> findAll();
}
