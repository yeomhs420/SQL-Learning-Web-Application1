package com.example.demo.jpa.repository.sampledata.join;

import com.example.demo.entity.sampledata.join.Leisure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeisureRepository extends JpaRepository<Leisure, Integer> {

}
