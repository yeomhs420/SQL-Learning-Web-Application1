package com.example.demo.jpa.repository;

import com.example.demo.entity.sampledata.RegionalRecoveryCostInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionalRecoveryCostInfoRepository extends JpaRepository<RegionalRecoveryCostInfo, Integer> {

    List<RegionalRecoveryCostInfo> findByIdBetween(Long id1, Long id2);

    List<RegionalRecoveryCostInfo> findByNameStartingWith(String name);

    List<RegionalRecoveryCostInfo> findFirst2ByName(String name);

}
