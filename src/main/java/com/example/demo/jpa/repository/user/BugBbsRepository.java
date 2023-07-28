package com.example.demo.jpa.repository.user;
import com.example.demo.entity.user.Bbs;
import com.example.demo.entity.user.BugBbs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BugBbsRepository extends JpaRepository<BugBbs, Long> {

    @Override
    List<BugBbs> findAll();
    @Override
    @EntityGraph(attributePaths = {"comments", "user"})   // comments, user fetch join
    Page<BugBbs> findAll(Pageable pageable);

    @Query("SELECT DISTINCT b FROM BugBbs b LEFT JOIN FETCH b.comments WHERE b.id = :bbsId")
    BugBbs findByIdWithBugComments(@Param("bbsId") Long bbsId);

    @Query("SELECT b FROM BugBbs b LEFT JOIN FETCH b.comments WHERE b.Title LIKE %:title%")
    List<BugBbs> findByTitleWithComments(@Param("title") String title);

    @Query("SELECT b FROM BugBbs b LEFT JOIN FETCH b.comments WHERE b.Content LIKE %:content%")
    List<BugBbs> findByContentWithComments(@Param("content") String content);

    @Query("SELECT b FROM BugBbs b LEFT JOIN FETCH b.comments WHERE b.user.userName LIKE %:writer%")
    List<BugBbs> findByWriterWithComments(@Param("writer") String writer);
}
