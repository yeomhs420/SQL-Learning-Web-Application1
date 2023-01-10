package com.example.demo;


import com.example.demo.entity.user.User;
import com.example.demo.jpa.repository.user.UserRepository;
import com.example.demo.service.EagerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HJMTest {

    @Autowired MockMvc mockMvc;

    @Autowired UserRepository userRepository;

    @Autowired User mockuser;

    @Autowired EagerService eagerService;

    @Test
    public void Unit13_Test() throws Exception{
        int unit = 13;
        int question1 = 4;
        int question2 = 3;
        String question3 = "SELECT * FROM EMPLOYEE ORDER BY SALARY DESC";
        String requestBody = "{\"unit\":"+unit+", \"question1\": "+question1+", \"question2\": "+question2+", \"question3\": \""+question3+"\"}";

        userRepository.save(mockuser);

        mockMvc.perform(post("/test/grading")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr("user",mockuser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionList").isArray())
                .andExpect(jsonPath("$.questionList[0].isCorrect").value(true))
                .andExpect(jsonPath("$.questionList[1].isCorrect").value(true))
                .andExpect(jsonPath("$.questionList[2].isCorrect").value(true))
                .andDo(print());
        userRepository.deleteAll();
    }

    @Test
    public void AOP_Operating_Test() throws Exception{
        int unit_num1 = 1;
        int unit_num2 = 17;
        int question1_1 = 5;
        int question1_2 = 2;
        int question17_1 = 3;
        int question17_2 = 5;
        String question1_3 = "SELECT NAME, POSITION, SALARY FROM EMPLOYEE";
        String question17_3 = "SELECT STUDENT_ID, STUDENT_NAME FROM MENTEE_MENTO WHERE MENTO_ID IN(SELECT MENTO_ID FROM MENTEE_MENTO GROUP BY MENTO_ID HAVING COUNT(MENTO_ID)>=3);";
        String requestBody = "{\"unit\":"+unit_num1+", \"question1\": "+question1_1+", \"question2\": "+question1_2+", \"question3\": \""+question1_3+"\"}";
        String requestBody2 = "{\"unit\":"+unit_num2+", \"question1\": "+question17_1+", \"question2\": "+question17_2+", \"question3\": \""+question17_3+"\"}";

        userRepository.save(mockuser);

        mockMvc.perform(post("/test/grading") //unit 1 정답 입력
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr("user",mockuser))
                .andExpect(status().isOk());

        User user = eagerService.getUserWithEagerProgress(mockuser.getUserID());

        assertEquals(user.getLearningStatus(),1);
        assertEquals(user.getProgress().get(unit_num1- 1), true);

        mockMvc.perform(post("/test/grading") //중복된 문제 해결시 learningstatus 증가 안하는 것을 확인
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr("user",mockuser))
                .andExpect(status().isOk());

        user = eagerService.getUserWithEagerProgress(mockuser.getUserID());

        assertEquals(user.getLearningStatus(),1);
        assertEquals(user.getProgress().get(unit_num1- 1), true);


        mockMvc.perform(post("/test/grading") // unit 17 정답 입력
                        .content(requestBody2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr("user",mockuser))
                .andExpect(status().isOk());

        user = eagerService.getUserWithEagerProgress(mockuser.getUserID());

        assertEquals(user.getLearningStatus(),2);
        assertEquals(user.getProgress().get(unit_num2- 1), true);

    }
}

