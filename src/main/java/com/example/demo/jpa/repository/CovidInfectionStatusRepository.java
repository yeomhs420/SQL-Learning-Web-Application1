package com.example.demo.jpa.repository;

import com.example.demo.entity.sampledata.CovidInfectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CovidInfectionStatusRepository extends JpaRepository<CovidInfectionStatus, Integer> {
    List<CovidInfectionStatus> findByDateBetween(Date startDate, Date endDate);
}
