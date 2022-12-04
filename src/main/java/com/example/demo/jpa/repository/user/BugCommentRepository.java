package com.example.demo.jpa.repository.user;

import com.example.demo.entity.user.BugComment;
import com.example.demo.entity.user.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BugCommentRepository extends JpaRepository<BugComment, Long> {

    @Query(value = "SELECT * FROM bug_comment WHERE bug_Bbs_id = :bbsId", nativeQuery = true)
    List<BugComment> findByBbsId(Long bbsId);
}
