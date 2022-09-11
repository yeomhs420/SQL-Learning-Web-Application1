package com.example.demo.service.test;

import com.example.demo.entity.sampledata.CovidVaccinationCenter;
import com.example.demo.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class SQLResultService {

    @Autowired
    private TestMapper testMapper;

    public List<Map<String, Object>> getResult(String sql){
        List<Map<String, Object>> resultList;
        try { resultList=testMapper.getResult(sql); }
        catch (Exception e) { return null; }
        return testMapper.getResult(sql);
    }

    public boolean checkKeywords(BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("error_msg", result.getAllErrors().get(0).getCode());
            return false;
        }
        return true;
    }

    public boolean checkSyntax(List<Map<String, Object>> resultList, Model model) {
        if(resultList==null) {
            model.addAttribute("error_msg", "Invaild SQL Syntax");
            return false;
        }
        return true;
    }

}
