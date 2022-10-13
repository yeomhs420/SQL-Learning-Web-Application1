package com.example.demo.service.sampledata.join;

import com.example.demo.entity.sampledata.join.ThePersonInCharge;
import com.example.demo.template.CsvTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThePersonInChargeService {

    @Autowired
    CsvTemplate csvTemplate;

    public List<ThePersonInCharge> getAllData() {

        ArrayList<ThePersonInCharge> thePersonInChargeList = new ArrayList<>();
        String csvClassPath = "classpath:static/csv/the_person_in_charge.csv";

        List<List<String>> list = csvTemplate.getOpenData(csvClassPath);

        if(list==null) return null;

        for(int i=0;i<list.size();i++){
            ThePersonInCharge thePersonInCharge = new ThePersonInCharge();
            thePersonInCharge.setId(Integer.parseInt(list.get(i).get(0)));
            thePersonInCharge.setName(list.get(i).get(1));
            thePersonInCharge.setPosition(list.get(i).get(2));
            thePersonInCharge.setDepartment(list.get(i).get(3));
            thePersonInCharge.setSalary(Integer.parseInt(list.get(i).get(4)));
            thePersonInCharge.setAge(Integer.parseInt(list.get(i).get(5)));
            thePersonInCharge.setPhone(list.get(i).get(6));
            thePersonInCharge.setLeave(list.get(i).get(7));
            thePersonInChargeList.add(thePersonInCharge);
        }

        return thePersonInChargeList;
    }
}
