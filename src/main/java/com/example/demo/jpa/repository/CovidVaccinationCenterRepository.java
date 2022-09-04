package com.example.demo.jpa.repository;

import com.example.demo.entity.sampledata.CovidVaccinationCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CovidVaccinationCenterRepository extends JpaRepository<CovidVaccinationCenter, Integer> {
    List<CovidVaccinationCenter> findByAddressLike(String prefix);
    List<CovidVaccinationCenter> findByPhoneIsNull();
}
