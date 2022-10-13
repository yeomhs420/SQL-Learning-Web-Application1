package com.example.demo.jpa.repository.join;

import com.example.demo.entity.sampledata.join.ThePersonInCharge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThePersonInChargeRepository extends JpaRepository<ThePersonInCharge, Integer> {
}
