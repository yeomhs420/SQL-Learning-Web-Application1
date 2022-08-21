package com.example.demo.entity.sampledata;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class AgriFoodInfo {

    @Id
    private String food_Code; // 음식코드
    private String large_Name; // 대분류명
    private String middle_Name; // 중분류명
    private String food_Name; // 음식명
    private double food_Volume; // 식품 중량
}
