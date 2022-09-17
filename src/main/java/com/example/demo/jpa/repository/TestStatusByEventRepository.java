package com.example.demo.jpa.repository;

import com.example.demo.entity.sampledata.TestStatusByEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface TestStatusByEventRepository extends JpaRepository<TestStatusByEvent, Integer> {
}
