package com.example.demo.jpa.repository.join;

import com.example.demo.entity.sampledata.join.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
