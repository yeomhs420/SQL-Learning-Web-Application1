package com.example.demo.sampleobject;

import lombok.Data;

@Data
public class CovidVaccinationCenter {
    private int id; // 연번
    private String name; // 센터명
    private String phone; // 사무실전화번호
    private String address; // 주소
    private String postalCode; // 우편번호
}
