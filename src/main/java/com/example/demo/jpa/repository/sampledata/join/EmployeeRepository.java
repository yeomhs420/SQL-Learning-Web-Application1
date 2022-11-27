package com.example.demo.jpa.repository.sampledata.join;

import com.example.demo.entity.sampledata.join.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
