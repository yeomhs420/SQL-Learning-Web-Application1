package com.example.demo.entity.sampledata;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class MenteeMento {

    @Id
    private int studentId;
    private String studentName;
    private int mentoId;
}
