package com.example.demo.service;

import com.example.demo.entity.user.User;
import com.example.demo.jpa.repository.user.UserRepository;
import com.example.demo.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    HttpSession session;

    @Autowired
    EagerService eagerService;
    @Autowired
    private UserRepository userRepository;


    public List<LinkedHashMap<String, Object>> getSQLResult(String sql){
        List<LinkedHashMap<String, Object>> resultList;
        try {
            resultList=testMapper.getResult(sql); //  Null이 결과인 컬럼은 Map의 키에 저장되지 않고 누락되기 때문에 설정 필요
        }
        catch (Exception e) { return null; }
        return testMapper.getResult(sql);
    }
    @Transactional("userTransactionManager")
    public void setStatus(int unit){
        User user = (User)session.getAttribute("user");
        user = eagerService.getUserWithEagerProgress(user.getUserID());

        if(user.getProgress().get(unit - 1).equals(true)) return;

        user.setLearningStatus(user.getLearningStatus()+1);
        user.getProgress().set(unit -1,true);

        userRepository.save(user);
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(7200);

    }
}
