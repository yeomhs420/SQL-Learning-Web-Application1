package com.example.demo.jpa.repository.join;

import com.example.demo.entity.sampledata.join.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
