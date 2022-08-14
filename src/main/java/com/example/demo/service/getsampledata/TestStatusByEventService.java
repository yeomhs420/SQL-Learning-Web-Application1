package com.example.demo.service.getsampledata;

import com.example.demo.sampleobject.TestStatusByEvent;
import com.example.demo.template.CsvTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

@Service
public class TestStatusByEventService {

    @Autowired
    CsvTemplate csvTemplate;

    public List<TestStatusByEvent> getTestStatusByEvent() {

        ArrayList<TestStatusByEvent> testStatusByEventsList = new ArrayList<>();
        String csvClassPath = "classpath:static/csv/test_status_by_event.csv";
        List<List<String>> list = csvTemplate.getOpenData(csvClassPath);

        System.out.println(list);

        if(list==null) return null;

        for(int i=0;i<list.size();i++){
            TestStatusByEvent testStatusByEvent = new TestStatusByEvent();
            testStatusByEvent.setEvent(list.get(i).get(0));
            testStatusByEvent.setWrittenTestRegister(Integer.parseInt(list.get(i).get(1)));
            testStatusByEvent.setWrittenTestExamination(Integer.parseInt(list.get(i).get(2)));
            testStatusByEvent.setWrittenTestPass(Integer.parseInt(list.get(i).get(3)));
            testStatusByEvent.setWrittenTestPassRate(Double.parseDouble(list.get(i).get(4)));
            testStatusByEvent.setPracticalTestRegister(Integer.parseInt(list.get(i).get(5)));
            testStatusByEvent.setPracticalTestExamination(Integer.parseInt(list.get(i).get(6)));
            testStatusByEvent.setPracticalTestPass(Integer.parseInt(list.get(i).get(7)));
            testStatusByEvent.setPracticalTestPassRate(Double.parseDouble(list.get(i).get(8)));
            testStatusByEventsList.add(testStatusByEvent);
        }

        return testStatusByEventsList;
    }
}
