package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Topic {
    private int id;
    private String name;
    private List<Unit> unitList;
}
