package com.example.demo.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Component
@Aspect
public class AdvisorConfig {
    @Pointcut("execution(* com.example.demo.service.getsampledata.*.get*(..))")
    public void logPointcut(){}

    @Around("logPointcut()")
    public List<Object> logAdvisor(ProceedingJoinPoint joinPoint) throws Throwable {
        List<Object> returnList;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod(); // Pointcut으로 선정한 메소드 불러오기
        try {
            returnList = (List<Object>) joinPoint.proceed();
            System.out.println(method.getName()+"()에서 반환 받은 데이터 수 : "+returnList.size());
        } catch (Throwable throwable) {
            throw throwable;
        }
        return returnList;
    }
}
