package com.example.demo.vo;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private int num;
    private boolean IsCorrect;
    private String userAnswer; // 사용자가 선택한 답 (객관식, SQL문 모두 해당)
    private String errorMsg; // SQL문 작성 문제의 경우 SQL 검증
    private List<List<String>> sqlResult; // SQL문 작성 문제의 경우 SQL 결과를 저장


}
