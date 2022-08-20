package com.example.demo.entity.sampledata;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class TestStatusByEvent {

    @Id @GeneratedValue
    private int id;

    private String event;
    private int writtenTestRegister;
    private int writtenTestExamination;
    private int writtenTestPass;
    private double writtenTestPassRate;
    private int practicalTestRegister;
    private int practicalTestExamination;
    private int practicalTestPass;
    private double practicalTestPassRate;

}
