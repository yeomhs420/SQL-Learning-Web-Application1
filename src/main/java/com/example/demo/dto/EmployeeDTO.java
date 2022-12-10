package com.example.demo.dto;

public class EmployeeDTO {

    int id;
    String name;
    String position;
    String department;
    int salary;
    int age;
    String phone;
    String leave;

    public EmployeeDTO() {

    }

    public EmployeeDTO(int id, String name, String position, String department, int salary, int age, String phone, String leave){

        this.id = id;
        this.name = name;
        this.position= position;
        this.department = department;
        this.salary = salary;
        this.age = age;
        this.phone = phone;
        this.leave = leave;

    }
}



