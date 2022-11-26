package com.example.demo.jpa.repository.sampledata;

import com.example.demo.entity.sampledata.RegionalRecoveryCostInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionalRecoveryCostInfoRepository extends JpaRepository<RegionalRecoveryCostInfo, Integer> {

    List<RegionalRecoveryCostInfo> findByIdBetween(Long id1, Long id2);

    List<RegionalRecoveryCostInfo> findByRegionStartingWith(String region);

    List<RegionalRecoveryCostInfo> findFirst2ByRegion(String region);

}
