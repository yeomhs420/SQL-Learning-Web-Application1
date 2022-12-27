package com.example.demo.vo;

import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class UserDto {

    private long id;

    @Size(min = 5, max = 20, message = "아이디는 5~20 자리로 입력해주세요.")
    private String userID;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$", message = "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.")
    private String userPassword;

    private String userName;

    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+", message = "이메일 형식이 맞지 않습니다.")
    private String userEmail;

    private int learningStatus;

    private List<Boolean> progress;

}