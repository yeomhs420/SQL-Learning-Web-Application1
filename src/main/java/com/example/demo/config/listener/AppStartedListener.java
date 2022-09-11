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

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        saveDBService.saveAllSampleData();
    }
}
