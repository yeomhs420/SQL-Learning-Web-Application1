package com.example.demo.entity.sampledata.join;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Assignment {

    @Id
    private String work;
    private int employeeId;
}
