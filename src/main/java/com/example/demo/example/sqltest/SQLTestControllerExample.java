package com.example.demo.example.sqltest;

import com.example.demo.validator.SQLValidator;
import com.example.demo.vo.SQLData;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

public class SQLTestControllerExample {

    @GetMapping("/test")
    public String testForm() {
        return "test/test_form";
    }

    @PostMapping("/test")
    public String testResult(@Valid SQLData sqlData, BindingResult result, Model model){
        if(result.hasErrors()) {
            // 에러 페이지로 return
        }
        return "test/test_form";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) { binder.setValidator(new SQLValidator()); } // SQLValidator을 bean으로 등록해서 직접 호출하는 경우에 이 코드는 필요없음
}
