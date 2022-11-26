package com.example.demo.entity.sampledata;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



@Data
@Entity
public class EducationCost {

    @Id
    @GeneratedValue
    private int id;

    private String subject;

    // 문제로 사용할 시 단위는 천 만임을 명시할 것
    private int totalcost;
    private int firstgrade;
    private int secondgrade;
    private int thirdgrade;

}
