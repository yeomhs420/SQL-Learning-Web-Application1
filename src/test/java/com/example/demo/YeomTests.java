package com.example.demo;

import com.example.demo.entity.user.Bbs;
import com.example.demo.entity.user.User;
import com.example.demo.vo.UserDto;
import com.example.demo.jpa.repository.user.BbsRepository;
import com.example.demo.jpa.repository.user.UserRepository;
import com.example.demo.service.BbsService;
import com.example.demo.vo.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class YeomTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BbsService bbsService;

    @Autowired
    BbsRepository bbsRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void Join_테스트() throws Exception{
        mockMvc.perform(get("/join"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void Login_테스트() throws Exception{

        String userID = "yeomhs420";
        String userPassword = "1234";

        UserDto userDto = new UserDto(1L, userID, userPassword, null, null, 0, null);

        mockMvc.perform(post("/login").param("userID", userID).param("userPassword", userPassword))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    public void 게시판_테스트() throws Exception{

        String userID = "yeomhs420";
        String userPassword = "1234";

        User user = new User(1L, userID, userPassword, "hello", null, 0, null);

        Bbs bbs = new Bbs(1L, "ha", user, "ha", null, null);

        userRepository.save(user);

        bbsRepository.save(bbs);

        assertEquals("ha", bbsService.getBbs(1L).getContent());

    }
}
