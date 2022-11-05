package com.example.demo.entity.sampledata.join;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Department {

    @Id
    private String division;
    private String code;
}
