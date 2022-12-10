package com.example.demo.config.listener;

import com.example.demo.service.SaveDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    SaveDBService saveDBService;

    // 애플리케이션이 시작되고 ApplicationContext가 생성되고 난 후에 실행되는 리스너 메소드
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        saveDBService.saveAllSampleData();
    }
}
