package com.example.demo.entity.sampledata;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
public class CovidVaccinationCenter {

    @Id
    private int id; // 연번

    private String name; // 센터명
    private String phone; // 사무실전화번호
    private String address; // 주소
    private String postalCode; // 우편번호
}
