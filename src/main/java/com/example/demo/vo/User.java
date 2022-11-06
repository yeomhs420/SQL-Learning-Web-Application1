package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    //사용자 번호
    private long user_num;
    //사용자 아이디
    private String user_id;
    //사용자 비밀번호
    private String user_pw;
    // 학습 현황
    private int learning_status;
    // 활동 포인트
    private int active_points;
}
