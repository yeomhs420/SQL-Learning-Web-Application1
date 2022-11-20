package com.example.demo.jpa.repository.sampledata.join;

import com.example.demo.entity.sampledata.join.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, String> {
}
