package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Unit {
    private int id;
    private int parentId;
    private String name;
    private String difficulty;
    private String learningTime;
    private String describe;
    private int question;
}
