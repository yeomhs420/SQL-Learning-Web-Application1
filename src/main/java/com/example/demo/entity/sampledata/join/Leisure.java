package com.example.demo.entity.sampledata.join;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Leisure {

    @Id
    private int employeeId;
    private String hobby;
}
