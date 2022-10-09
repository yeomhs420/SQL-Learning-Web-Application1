package com.example.demo.controller;

import com.example.demo.vo.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/learn")
public class LearnController {

    @Autowired
    List<Topic> topicList;

    @RequestMapping({"/", ""})
    public String list(Model model) {
        model.addAttribute("topicList", topicList);
        return "learn/topiclist";
    }

    @RequestMapping("/unit/{unit_num}")
    public String unit(@PathVariable("unit_num") String unit_num) {
        return "learn/unit/unit"+unit_num;
    }
}
