package com.example.demo.sampleobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class RecoveryCostInfo {
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
