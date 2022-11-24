package com.example.demo.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    //사용자 번호
    private long userNum;
    //사용자 아이디
    private String userId;
    //사용자 비밀번호
    private String userPw;
    // 학습 현황
    private int learningStatus;
    // 활동 포인트
    private int activePoints;
}
