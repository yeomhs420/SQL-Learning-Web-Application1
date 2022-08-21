package com.example.demo.entity.sampledata;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class RegionalRecoveryCostInfo {
    @Id
    @GeneratedValue
    private int id;

    private String region;

    private int totalCost;

    private int supportCost;

    private int treasuryCost;

    private int provincialCost;

    private int selfCost;

}
