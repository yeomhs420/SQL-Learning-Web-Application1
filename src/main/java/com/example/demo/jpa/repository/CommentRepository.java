package com.example.demo.jpa.repository;

import com.example.demo.entity.sampledata.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comment WHERE bbs_id = :bbsId", nativeQuery = true)
    List<Comment> findByBbsId(Long bbsId);

}
