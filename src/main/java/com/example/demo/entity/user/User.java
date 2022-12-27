package com.example.demo.entity.user;

import com.example.demo.vo.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임
    private long id;

    private String userID;

    private String userPassword;

    private String userName;

    private String userEmail;

    private int learningStatus;

    @Column(name="progress")
    @ElementCollection(targetClass=Boolean.class)
    private List<Boolean> progress;

    public static User createUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getUserID(),
                userDto.getUserPassword(),
                userDto.getUserName(),
                userDto.getUserEmail(),
                userDto.getLearningStatus(),
                userDto.getProgress()
        );
    }
}