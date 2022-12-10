package com.example.demo.controller;

import com.example.demo.vo.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    List<Topic> topicList;

    @GetMapping
    public String list(Model model) {
        List<String> stateList = new ArrayList<>();
        for(int i=0;i<17;i++) {
            if(i%2==0) stateList.add("[미해결]");
            else stateList.add("[해결]");
        }
        model.addAttribute("topicList", topicList);
        model.addAttribute("stateList", stateList);
        return "test/testlist";
    }

    @RequestMapping("/unit/{test_num}")
    public String unit(@PathVariable("test_num") String test_num, Model model) {
        model.addAttribute("test_num", test_num);
        return "test/unit/test"+test_num;
    }
}
