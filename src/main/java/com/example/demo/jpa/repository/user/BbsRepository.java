package com.example.demo.jpa.repository.user;

import com.example.demo.entity.user.Bbs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BbsRepository extends JpaRepository<Bbs, Long> {
    @Override
    List<Bbs> findAll();

    @Query("SELECT DISTINCT b FROM Bbs b LEFT JOIN FETCH b.comments WHERE b.id = :bbsId")
    Bbs findByIdWithComments(@Param("bbsId") Long bbsId);

}