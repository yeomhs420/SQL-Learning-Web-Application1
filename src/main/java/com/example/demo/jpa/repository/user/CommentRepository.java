package com.example.demo.jpa.repository.user;

import com.example.demo.entity.user.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comment WHERE bbs_id = :bbsId", nativeQuery = true)
    List<Comment> findByBbsId(Long bbsId);

}
