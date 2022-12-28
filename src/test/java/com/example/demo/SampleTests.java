package com.example.demo;

import com.example.demo.entity.sampledata.CovidVaccinationCenter;
import com.example.demo.entity.user.User;
import com.example.demo.jpa.repository.user.UserRepository;
import com.example.demo.service.sampledata.CovidVaccinationCenterService;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SampleTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	CovidVaccinationCenterService covidVaccinationCenterService;

	@Autowired
	UserRepository userRepository;


	@Test
	public void Home_테스트() throws Exception {
		mockMvc.perform(get("/home")) // "/home" 요청을 받는 핸들러가 있음
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void 존재하지_않는_페이지_테스트() throws Exception {
		mockMvc.perform(get("/fakepage")) // "/fakepage" 요청을 받는 핸들러가 없음
				.andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	public void Unit1_테스트() throws Exception {
		int unit=1;
		int question1 = 5;
		int question2 = 2;
		String question3 = "SELECT NAME, POSITION, SALARY FROM EMPLOYEE";
		String body = "{\"unit\":"+unit+", \"question1\": "+question1+", \"question2\": "+question2+", \"question3\": \""+question3+"\"}";
		mockMvc.perform(post("/test/grading")
						.content(body)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.questionList").isArray())
				.andExpect(jsonPath("$.questionList[0].isCorrect").value(true))
				.andExpect(jsonPath("$.questionList[1].isCorrect").value(true))
				.andExpect(jsonPath("$.questionList[2].isCorrect").value(true))
				.andDo(print());
	}

	@Test
	public void CovidVaccinationCenterServiceTest() throws Exception {
		List<CovidVaccinationCenter> covidVaccinationCenterList = covidVaccinationCenterService.getAllData(1, 100);
		assertEquals(covidVaccinationCenterList.size(), 100);
	}

	@Test(expected=NullPointerException.class)
	public void NullPointerExceptionTest() throws Exception {
		String str = null;
		int len = str.length();
	}

	@Test
	public void Testlist_테스트() throws Exception {
		User user = new User();
		user.setUserID("1");
		user.setUserPassword("!wndur0703");
		user.setUserName("김주역");
		user.setUserEmail("jooyeok42@naver.com");
		userRepository.save(user);

		mockMvc.perform(get("/test") // 로그인 상태
						.sessionAttr("user", user))
				.andExpect(status().isOk())
				.andExpect(view().name("test/testlist"))
				.andDo(print());

		mockMvc.perform(get("/test")) // 비로그인 상태
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/login"))
				.andDo(print());
		userRepository.delete(user);
	}

	@Test
	@AfterAll
	public void userRepository_롤백테스트() throws Exception {
		List<User> userList = userRepository.findAll();
		assertEquals(userList.size(), 0);
	}
}