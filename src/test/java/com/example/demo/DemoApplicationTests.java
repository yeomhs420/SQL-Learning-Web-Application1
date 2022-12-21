package com.example.demo;

import com.example.demo.entity.sampledata.join.QAssignment;
import com.example.demo.entity.sampledata.join.QLeisure;
import com.example.demo.entity.user.User;
import com.example.demo.vo.Question;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTests {


	@Autowired
	MockMvc mockMvc;

	@Autowired
	DataSource dataSource;

	@Test
	public void lettJoinTest() {
		// this.mockMvc.perform().andExpect(model().attribute("name", "Spring"))
	}

}
