package com.example.demo;

import com.example.demo.entity.user.User;
import com.example.demo.jpa.repository.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChanTests {
    @Autowired MockMvc mockMvc;
    @Autowired UserRepository userRepository;
    @Autowired User mockUser;

    @Test
    public void TestController_list() throws Exception {
        MockHttpSession session = new MockHttpSession();
        mockMvc.perform(get("/test").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andDo(print());
    }

    @Test(expected=NumberFormatException.class)
    public void NumberFormatExceptionTest() throws Exception {
        String data = "이건안돼요";
        int val = Integer.parseInt(data);
    }

    @Test
    public void Unit6_test() throws Exception {
        int unit=6;
        int question1 = 5;
        int question2 = 4;
        int question3 = 3;
        String body = "{\"unit\":"+unit+", \"question1\": "+question1+", \"question2\": "+question2+", \"question3\": \""+question3+"\"}";

        userRepository.save(mockUser);

        mockMvc.perform(post("/test/grading")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr("user", mockUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionList[0].isCorrect").value(true))
                .andExpect(jsonPath("$.questionList[1].isCorrect").value(true))
                .andExpect(jsonPath("$.questionList[2].isCorrect").value(true))
                .andDo(print());
        userRepository.deleteAll();
    }

}