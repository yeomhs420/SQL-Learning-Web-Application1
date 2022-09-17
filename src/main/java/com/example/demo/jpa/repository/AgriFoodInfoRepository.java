package com.example.demo.jpa.repository;

import com.example.demo.entity.sampledata.AgriFoodInfo;
import com.example.demo.entity.sampledata.CovidInfectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AgriFoodInfoRepository extends JpaRepository<AgriFoodInfo, Integer> {
    List<AgriFoodInfo> findByFood_NameLike(String galbi);
    List<AgriFoodInfo> findByFood_CodeStartingWith(String C);
    List<AgriFoodInfo> findByFood_VolumeBetween(float min, float max);
}


