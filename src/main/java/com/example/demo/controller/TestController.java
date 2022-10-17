package com.example.demo.controller;

import com.example.demo.vo.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    List<Topic> topicList;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("topicList", topicList);
        return "test/testlist";
    }

    @RequestMapping("/unit/{test_num}")
    public String unit(@PathVariable("test_num") String test_num, Model model) {
        model.addAttribute("test_num", test_num);
        return "test/unit/test"+test_num;
    }
}
