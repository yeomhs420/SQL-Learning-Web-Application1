package com.example.demo.vo;

import lombok.Data;

import java.util.List;

@Data
public class TestResult {
    private int correctCount;
    private List<Question> questionList;
}
