package com.example.demo.entity.sampledata.join;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Employee {

    @Id
    private int id;

    private String name;
    private String position;
    private String department;
    private int salary;
    private int age;
    private String phone;
    private String leave;
}
