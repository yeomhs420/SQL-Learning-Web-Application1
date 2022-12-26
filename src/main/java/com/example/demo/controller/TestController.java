package com.example.demo.controller;

import com.example.demo.entity.user.User;
import com.example.demo.jpa.repository.user.UserRepository;
import com.example.demo.vo.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional("userTransactionManager")
@RequestMapping("/test")
public class TestController {

    @Autowired
    List<Topic> topicList;

    @Autowired
    HttpSession session;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String list(Model model, RedirectAttributes re) {
        List<String> stateList = new ArrayList<>();
        User user = (User)session.getAttribute("user");
        if(user == null) {
            re.addFlashAttribute("msg", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        user = userRepository.findByUserId(user.getUserID()).get(0);
        for(int i=0;i<user.getProgress().size();i++) {
            if(user.getProgress().get(i)==false) stateList.add("[미해결]");
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