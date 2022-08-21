package com.example.demo.entity.sampledata;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
//@Table(name = "COVID_INFECTION_STATUS")
public class CovidInfectionStatus {

    @Id
    private int dataNum; // 데이터 번호

    @Temporal(TemporalType.DATE)
    private Date date; // 날짜

    private int accExamCnt; // 누적 검사 수
    private int accDecideCnt; // 누적 확진자 수
    private int accDeathCnt; // 누적 사망자 수
}
