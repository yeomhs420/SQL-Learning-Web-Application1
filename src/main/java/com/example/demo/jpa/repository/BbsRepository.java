package com.example.demo.jpa.repository;

import com.example.demo.entity.sampledata.Bbs;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BbsRepository extends JpaRepository<Bbs, Long> {

    @Override
    List<Bbs> findAll();

}