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
    public TestResult gradingUnit1(@RequestBody Map<Integer, Object> userAnswer, @ModelAttribute SQLData sqlData, BindingResult bindingResult) { // {"1": 4, "2": "select * from member;"}
        System.out.println(userAnswer.toString());
        System.out.println(userAnswer.get(1));
        String userAnswer2 = userAnswer.get(2).toString();
        sqlData.setSql(userAnswer2);

        TestResult testResult = new TestResult();
        sqlValidator.validate(sqlData, bindingResult);
        if(bindingResult.hasErrors()) {
            testResult.setErrorMsg(bindingResult.getAllErrors().get(0).getCode());
            return testResult;
        }
        List<Map<String, Object>> sqlResult = testService.getSQLResult(userAnswer2);
        if(sqlResult==null) {
            testResult.setErrorMsg("Invaild SQL Syntax");
            return testResult;
        }
        System.out.println(sqlResult.size());
        testResult.setCorrectCount(5);

        List<Question> questionList = new ArrayList<>();

        Question question = new Question();
        question.setNum(2);
        question.setIsCorrect(true);
        question.setUserAnswer(userAnswer2);

        List<List<String>> orderedSqlResult = new ArrayList<>();

        List<String> row = new ArrayList<>();
        row.add("ID");
        row.add("NAME");
        row.add("AGE");
        orderedSqlResult.add(row);

        question.setSqlResult(orderedSqlResult);
        questionList.add(question);
        testResult.setQuestionList(questionList);
        return testResult;
    }
}
