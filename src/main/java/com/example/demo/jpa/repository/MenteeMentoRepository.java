package com.example.demo.jpa.repository;

import com.example.demo.entity.sampledata.MenteeMento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenteeMentoRepository extends JpaRepository<MenteeMento, Integer> {
}
