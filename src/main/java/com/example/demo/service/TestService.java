package com.example.demo.service;

import com.example.demo.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public List<LinkedHashMap<String, Object>> getSQLResult(String sql){
        List<LinkedHashMap<String, Object>> resultList;
        try { resultList=testMapper.getResult(sql); }
        catch (Exception e) { return null; }
        return testMapper.getResult(sql);
    }
}
