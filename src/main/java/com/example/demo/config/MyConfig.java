package com.example.demo.config;

import com.example.demo.entity.user.User;
import com.example.demo.vo.Topic;
import com.example.demo.vo.Unit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyConfig {

    @Bean
    public List<Topic> topicList() {
        List<Topic> topicList = new ArrayList<>();

        List<Unit> firstUnitList = new ArrayList<>();
        firstUnitList.add(new Unit(1, 1, "기본 문법", "중하", "10분", "기본적인 SQL 작성법과 테이블의 row를 조회하는 SELECT 절", 3));
        firstUnitList.add(new Unit(2, 1, "INSERT", "중하", "5분", "테이블에 row를 삽입하는 INSERT 절", 2));
        firstUnitList.add(new Unit(3, 1, "UPDATE", "하", "3분", "테이블의 row의 데이터를 변경하는 UPDATE 절", 2));
        firstUnitList.add(new Unit(4, 1, "DELETE", "하", "3분", "테이블의 row를 삭제하는 DELETE 절", 2));
        topicList.add(new Topic(1, "MySQL 기초", firstUnitList));

        List<Unit> secondUnitList = new ArrayList<>();
        secondUnitList.add(new Unit(5, 2, "문자열 함수", "중하", "5분", "MySQL이 제공하는 다양한 문자열 함수", 3));
        secondUnitList.add(new Unit(6, 2, "수학 함수", "하", "3분", "MySQL이 제공하는 다양한 수학 함수",3));
        secondUnitList.add(new Unit(7, 2, "날짜/시간 함수", "중하", "5분", "MySQL이 제공하는 다양한 날짜/시간 함수",3));
        secondUnitList.add(new Unit(8, 2, "그룹 함수", "하", "3분", "MySQL이 제공하는 다양한 그룹 함수",3));
        topicList.add(new Topic(2, "MySQL 내장 함수", secondUnitList));

        List<Unit> thirdUnitList = new ArrayList<>();
        thirdUnitList.add(new Unit(9, 3, "비교 연산자", "중하", "7분", "피연산자들을 비교하는 비교 연산자",3));
        thirdUnitList.add(new Unit(10, 3, "논리 연산자", "중하", "3분", "논리식을 판단하여 참 또는 거짓을 반환하는 논리 연산자",3));
        thirdUnitList.add(new Unit(11, 3, "흐름 제어", "중상", "10분", "순차적인 흐름을 제어하는 방법",3));
        thirdUnitList.add(new Unit(12, 3, "LIKE", "중하", "5분", "데이터의 특정 패턴을 검색하기 위한 LIKE 연산자",3));
        topicList.add(new Topic(3, "검색 조건", thirdUnitList));

        List<Unit> fourthUnitList = new ArrayList<>();
        fourthUnitList.add(new Unit(13, 4, "ORDER BY", "중하", "5분", "테이블을 정렬하는 ORDER BY절",3));
        fourthUnitList.add(new Unit(14, 4, "GROUP BY", "중", "5분", "특정 컬럼을 기준으로 데이터를 묶는 GROUP BY 절",3));
        fourthUnitList.add(new Unit(15, 4, "HAVING", "중", "5분", "GROUP BY 절에 의해 그룹핑된 결과 집합의 조건을 설정하는 HAVING 절", 2));
        topicList.add(new Topic(4, "정렬과 그룹핑", fourthUnitList));

        List<Unit> fifthUnitList = new ArrayList<>();
        fifthUnitList.add(new Unit(16, 5, "JOIN", "중상", "10분", "피연산자들을 비교하는 비교 연산자",3));
        fifthUnitList.add(new Unit(17, 5, "서브쿼리", "상", "12분", "쿼리 내부에 또 다른 쿼리를 포함하는 방법",3));
        topicList.add(new Topic(5, "다중 테이블", fifthUnitList));

        return topicList;
    }

    @Bean
    public User mockUser() {
        User user = new User();
        user.setUserID("jooyeok");
        user.setUserPassword("!wndur0703");
        user.setUserName("김주역");
        user.setUserEmail("jooyeok42@naver.com");
        List<Boolean> progress = new ArrayList<>();
        for (int i = 0; i < 17; i++) progress.add(false);
        user.setProgress(progress);
        return user;
    }
}
