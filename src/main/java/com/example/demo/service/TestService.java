package com.example.demo.service;

import com.example.demo.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public List<LinkedHashMap<String, Object>> getSQLResult(String sql){
        List<LinkedHashMap<String, Object>> resultList;
        try {
            resultList=testMapper.getResult(sql); //  Null이 결과인 컬럼은 Map의 키에 저장되지 않고 누락되기 때문에 설정 필요
        }
        catch (Exception e) { return null; }
        return testMapper.getResult(sql);
    }

    public int setStatus(){
        getUserEagerProgress()
    }
}
