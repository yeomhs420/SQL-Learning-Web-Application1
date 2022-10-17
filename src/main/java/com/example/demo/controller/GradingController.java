package com.example.demo.controller;

import com.example.demo.service.TestService;
import com.example.demo.validator.SQLValidator;
import com.example.demo.vo.Question;
import com.example.demo.vo.SQLData;
import com.example.demo.vo.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test/grading")
public class GradingController {

    @Autowired SQLValidator sqlValidator;
    @Autowired TestService testService;

    @PostMapping
    public TestResult gradingUnit1(@RequestBody Map<String, Object> userAnswer, @ModelAttribute SQLData sqlData, BindingResult bindingResult) { // {"1": 4, "2": "select * from member;"}
        int unit = Integer.parseInt(userAnswer.get("unit").toString());
        int answer1 = Integer.parseInt(userAnswer.get("question1").toString());
        int answer2 = Integer.parseInt(userAnswer.get("question2").toString());
        String answer3 = userAnswer.get("question3").toString();

        System.out.println(unit);
        System.out.println(answer1);
        System.out.println(answer2);
        System.out.println(answer3);

        sqlData.setSql(answer3);

        TestResult testResult = new TestResult();

        sqlValidator.validate(sqlData, bindingResult);
        if(bindingResult.hasErrors()) {
            testResult.setErrorMsg(bindingResult.getAllErrors().get(0).getCode());
            return testResult;
        }

        List<Map<String, Object>> sqlResult = testService.getSQLResult(answer3);
        if(sqlResult==null) {
            testResult.setErrorMsg("Invaild SQL Syntax");
            return testResult;
        }

        System.out.println(sqlResult.size());

        for(int i=0;i<sqlResult.size();i++) {
            System.out.println(sqlResult.get(i).toString());
        }
        testResult.setCorrectCount(3);

        List<Question> questionList = new ArrayList<>();

        Question question1 = new Question();
        question1.setNum(1);
        question1.setIsCorrect(true);
        question1.setUserAnswer(String.valueOf(answer1));
        questionList.add(question1);

        Question question2 = new Question();
        question2.setNum(2);
        question2.setIsCorrect(true);
        question2.setUserAnswer(String.valueOf(answer2));
        questionList.add(question2);

        Question question3 = new Question();
        question3.setNum(3);
        question3.setIsCorrect(true);
        question3.setUserAnswer(answer3);
        List<List<String>> orderedSqlResult = new ArrayList<>();
        List<String> row = new ArrayList<>();
        row.add("ID");
        row.add("NAME");
        row.add("AGE");
        orderedSqlResult.add(row);
        question3.setSqlResult(orderedSqlResult);
        questionList.add(question3);

        testResult.setQuestionList(questionList);
        return testResult;
    }
}
