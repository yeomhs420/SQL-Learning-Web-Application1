package com.example.demo;

import com.example.demo.entity.sampledata.CovidVaccinationCenter;
import com.example.demo.service.sampledata.CovidVaccinationCenterService;
import org.junit.Test;
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


	@Test
	public void Home_테스트() throws Exception {
		mockMvc.perform(get("/home"))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void 존재하지_않는_페이지_테스트() throws Exception {
		mockMvc.perform(get("/fakepage"))
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

}
