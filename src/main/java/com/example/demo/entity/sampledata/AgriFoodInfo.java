package com.example.demo.entity.sampledata;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class AgriFoodInfo {

    @Id
    private String foodCode; // 음식코드
    private String largeName; // 대분류명
    private String middleName; // 중분류명
    private String foodName; // 음식명
    private double foodVolume; // 식품 중량
}
