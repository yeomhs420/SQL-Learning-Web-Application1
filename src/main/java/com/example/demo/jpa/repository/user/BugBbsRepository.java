package com.example.demo.jpa.repository.user;
import com.example.demo.entity.user.Bbs;
import com.example.demo.entity.user.BugBbs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BugBbsRepository extends JpaRepository<BugBbs, Long> {

    @Override
    List<BugBbs> findAll();

    @Query("SELECT DISTINCT b FROM BugBbs b LEFT JOIN FETCH b.comments WHERE b.id = :bbsId")
    BugBbs findByIdWithBugComments(@Param("bbsId") Long bbsId);
}
