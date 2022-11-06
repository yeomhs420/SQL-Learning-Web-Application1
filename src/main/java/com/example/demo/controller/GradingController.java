package com.example.demo.controller;

import com.example.demo.service.GradingService;
import com.example.demo.vo.SQLData;
import com.example.demo.vo.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test/grading")
public class GradingController {

    @Autowired GradingService gradingService;

    @PostMapping
    public TestResult gradingUnit(@RequestBody Map<String, Object> userAnswer, @ModelAttribute SQLData sqlData, BindingResult bindingResult) { // {"1": 4, "2": "select * from member;"}
        return gradingService.grade(userAnswer, sqlData, bindingResult);
    }
}
