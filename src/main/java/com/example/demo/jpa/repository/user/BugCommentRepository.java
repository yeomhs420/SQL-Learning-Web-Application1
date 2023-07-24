package com.example.demo.jpa.repository.user;

import com.example.demo.entity.user.BugComment;
import com.example.demo.entity.user.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BugCommentRepository extends JpaRepository<BugComment, Long> {

}
