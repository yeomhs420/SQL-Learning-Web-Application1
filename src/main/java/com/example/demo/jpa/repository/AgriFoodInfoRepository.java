package com.example.demo.jpa.repository;

import com.example.demo.entity.sampledata.AgriFoodInfo;
import com.example.demo.entity.sampledata.CovidInfectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AgriFoodInfoRepository extends JpaRepository<AgriFoodInfo, Integer> {
    List<AgriFoodInfo> findByFoodNameLike(String galbi);
    List<AgriFoodInfo> findByFoodCodeStartingWith(String C);
    List<AgriFoodInfo> findByFoodVolumeBetween(float min, float max);
}


