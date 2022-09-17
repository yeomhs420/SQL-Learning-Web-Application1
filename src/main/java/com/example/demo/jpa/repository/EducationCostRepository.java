package com.example.demo.jpa.repository;

import com.example.demo.entity.sampledata.EducationCost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationCostRepository extends JpaRepository<EducationCost, Integer> {

    List<EducationCost> findBytotalcostLessThan(Long totalcost);

    List<EducationCost> findBysubjectStartingWith(String subject);

    List<EducationCost> findBysubjectContaining(String subject);

}