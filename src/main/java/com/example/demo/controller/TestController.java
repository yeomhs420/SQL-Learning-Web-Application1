package com.example.demo.controller;

import com.example.demo.entity.user.User;
import com.example.demo.jpa.repository.user.UserRepository;
import com.example.demo.vo.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.plugin.dom.core.Element;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
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
        System.out.println("-----");
        System.out.println(user);
        System.out.println(user.getProgress());
//        System.out.println(user.getProgress().getClass().getName());
        System.out.println("-----");

        for(int i=0;i<17;i++) {
            user.getProgress().add(i, (user.getProgress().get(i) == null) ? false : false);
        }

        for(int i=0;i<17;i++) {
            if(user.getProgress().get(i)==false){
                stateList.add("[미해결]");
            }
            else{
                stateList.add("[해결]");
//                if(user.getProgress().get(i)==true) stateList.add("[해결]");
//                else stateList.add("[미해결]");
            }
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
