package com.example.demo.jpa.repository.user;

import com.example.demo.entity.user.Bbs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BbsRepository extends JpaRepository<Bbs, Long> {
    @Override
    List<Bbs> findAll();
    @Override
    @EntityGraph(attributePaths = {"comments", "user"})   // comments, user fetch join
    Page<Bbs> findAll(Pageable pageable);

    @Query("SELECT DISTINCT b FROM Bbs b LEFT JOIN FETCH b.comments WHERE b.id = :bbsId")
    Bbs findByIdWithComments(@Param("bbsId") Long bbsId);

    @Query("SELECT b FROM Bbs b LEFT JOIN FETCH b.comments WHERE b.Title LIKE %:title%")
    List<Bbs> findByTitleWithComments(@Param("title") String title);

    @Query("SELECT b FROM Bbs b LEFT JOIN FETCH b.comments WHERE b.Content LIKE %:content%")
    List<Bbs> findByContentWithComments(@Param("content") String content);

    @Query("SELECT b FROM Bbs b LEFT JOIN FETCH b.comments WHERE b.user.userName LIKE %:writer%")
    List<Bbs> findByWriterWithComments(@Param("writer") String writer);



}