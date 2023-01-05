package com.example.demo.config.aop;

import com.example.demo.vo.TestResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.List;

@Configuration
@Aspect
public class AdvisorConfig {
    @Pointcut("execution(* com.example.demo.service.sampledata..get*(..))")
    public void logPointcut(){}

    @Around("logPointcut()")
    public List<Object> logAdvisor(ProceedingJoinPoint joinPoint) throws Throwable {
        List<Object> returnList;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod(); // Pointcut으로 선정한 메소드 불러오기
        try {
            returnList = (List<Object>) joinPoint.proceed();
            System.out.println(method.getDeclaringClass()+"."+method.getName()+"()에서 반환 받은 데이터 수 : "+returnList.size());
        } catch (Throwable throwable) {
            throw throwable;
        }
        return returnList;
    }

    @Pointcut("execution(* com.example.demo.service.GradingService..gradeUnit*(..))")
    public void gradepointcut(){}

    @AfterReturning(value = "gradepointcut()", returning = "testResult")
    public void changeStatus(JoinPoint joinPoint, Object testResult) throws Throwable {

    }

}
