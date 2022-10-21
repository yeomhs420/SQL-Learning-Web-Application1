package com.example.demo.service;

import com.example.demo.entity.sampledata.join.Employee;
import com.example.demo.jpa.repository.join.EmployeeRepository;
import com.example.demo.validator.SQLValidator;
import com.example.demo.vo.Question;
import com.example.demo.vo.SQLData;
import com.example.demo.vo.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.*;

@Service
@Transactional
public class GradingService {

    @Autowired SQLValidator sqlValidator;
    @Autowired TestService testService;
    @Autowired EmployeeRepository employeeRepository;

    public TestResult grade(Map<String, Object> userAnswer, SQLData sqlData, BindingResult bindingResult) {
        TestResult testResult = new TestResult();
        int unit = Integer.parseInt(userAnswer.get("unit").toString());
        switch (unit) {
            case 1 : testResult = gradeUnit1(userAnswer, sqlData, bindingResult, testResult); break;
        }
        return testResult;
    }

    public List<LinkedHashMap<String, Object>> validateAndGetSqlResult(String answer, SQLData sqlData, BindingResult bindingResult, Question question) {
        List<LinkedHashMap<String, Object>> sqlResult = null;
        sqlData.setSql(answer);
        sqlValidator.validate(sqlData, bindingResult);
        if(bindingResult.hasErrors()) question.setErrorMsg(bindingResult.getAllErrors().get(0).getCode());
        else {
            sqlResult = testService.getSQLResult(answer);
            if(sqlResult==null) question.setErrorMsg("Invaild SQL Syntax");
        }
        return sqlResult;
    }

    public List<List<String>> getSqlResultForShow(Question question, List<LinkedHashMap<String, Object>> sqlResult) {
        List<List<String>> resultForShow = null;
        if(question.getErrorMsg()==null&&sqlResult!=null&&sqlResult.size()!=0) { // "사용자에게 보여줄" 채점 데이터를 담음
            resultForShow = new ArrayList<>();
            List<String> tempRow = new ArrayList<>();
            for(Map.Entry<String, Object> entry : sqlResult.get(0).entrySet()) tempRow.add(entry.getKey());
            resultForShow.add(tempRow);
            for(LinkedHashMap<String, Object> row : sqlResult) {
                tempRow = new ArrayList<>();
                for(Map.Entry<String, Object> entry : row.entrySet()) tempRow.add(entry.getValue().toString());
                resultForShow.add(tempRow);
            }
        }
        return resultForShow;
    }

    public void showSqlResult(List<List<String>> sqlResult) {
        if(sqlResult==null) {
            System.out.println("No Data");
            return;
        }
        for(int i=0;i<sqlResult.size();i++) {
            for(int j=0;j<sqlResult.get(i).size(); j++) System.out.print(sqlResult.get(i).get(j)+" ");
            System.out.println();
        }
    }

    public TestResult gradeUnit1(Map<String, Object> userAnswer, SQLData sqlData, BindingResult bindingResult, TestResult testResult) {
        int answer1 = Integer.parseInt(userAnswer.get("question1").toString());
        int answer2 = Integer.parseInt(userAnswer.get("question2").toString());
        String answer3 = userAnswer.get("question3").toString();

        List<Question> questionList = new ArrayList<>();

        // 문제 1 채점
        Question question1 = new Question();
        question1.setNum(1);
        question1.setUserAnswer(String.valueOf(answer1));
        if(answer1==5) {
            question1.setIsCorrect(true);
            testResult.setCorrectCount(testResult.getCorrectCount()+1);
        }


        // 문제 2 채점
        Question question2 = new Question();
        question2.setNum(2);
        question2.setUserAnswer(String.valueOf(answer2));
        if(answer2==2) {
            question2.setIsCorrect(true);
            testResult.setCorrectCount(testResult.getCorrectCount()+1);
        }


        // 문제 3 채점
        Question question3 = new Question();
        question3.setNum(3);
        question3.setUserAnswer(answer3);

        List<LinkedHashMap<String, Object>> sqlResult = validateAndGetSqlResult(answer3, sqlData, bindingResult, question3); // 사용자의 답안을 검증하고 sql 결과를 가져온다.
        question3.setSqlResult(getSqlResultForShow(question3, sqlResult)); // 사용자에게 보여줄 sql 결과를 List<List<String>> 타입으로 생성 후 저장
        // showSqlResult(question3.getSqlResult()); // 유저가 생성한 sql 결과를 확인

        List<Employee> employeeList = employeeRepository.findAll(); // 답안
        int correctCount=0;
        if(sqlResult!=null&&sqlResult.size()==5&&sqlResult.get(0).size()==3) {
            for(int i=0;i<5;i++) {
                if(sqlResult.get(i).containsKey("NAME")&&sqlResult.get(i).get("NAME").toString().equals(employeeList.get(i).getName())) correctCount++;
                if(sqlResult.get(i).containsKey("POSITION")&&sqlResult.get(i).get("POSITION").toString().equals(employeeList.get(i).getPosition())) correctCount++;
                if(sqlResult.get(i).containsKey("SALARY")&&sqlResult.get(i).get("SALARY").toString().equals(String.valueOf(employeeList.get(i).getSalary()))) correctCount++;
            }
            if(correctCount==15) {
                question3.setIsCorrect(true);
                testResult.setCorrectCount(testResult.getCorrectCount()+1);
            }
        }

        questionList.add(question1);
        questionList.add(question2);
        questionList.add(question3);
        testResult.setQuestionList(questionList);
        return testResult;
    }
}
