package com.example.demo.entity.sampledata;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Bbs extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임
    private long id;

    private String Title;

    private String Content;

    private String Datetime;    // 자동생성된 datetime 'T' 제거를 위한 필드

    @OneToMany
    @JoinColumn(name = "bbs_id")    // 양방향 매핑
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

}