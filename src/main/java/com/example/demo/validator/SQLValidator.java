package com.example.demo.validator;

import com.example.demo.vo.SQLData;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SQLValidator implements Validator {

    String[] bannedKeywords = {"CREATE", "ALTER", "DROP", "INSERT", "DELETE", "UPDATE", "COMMIT", "ROLLBACK", "GRANT", "REVOKE"};

    @Override
    public boolean supports(Class<?> clazz) {
        return SQLData.class.isAssignableFrom(clazz); // 클래스 배치 가능 여부
    }

    @Override
    public void validate(Object target, Errors errors) {
        String sql = ((SQLData) target).getSql().toUpperCase();
        if(sql.trim().length()==0) errors.rejectValue("sql", "Enter your answer");
        for(int i=0;i<bannedKeywords.length;i++){
            if(sql.contains(bannedKeywords[i])) {
                errors.rejectValue("sql", bannedKeywords[i]+" keyword is included in SQL");
                break;
            }
        }
    }
}
