package com.example.demo.service.sampledata.join;

import com.example.demo.entity.sampledata.join.Leisure;
import com.example.demo.template.CsvTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeisureService {

    @Autowired
    CsvTemplate csvTemplate;

    public List<Leisure> getAllData() {

        ArrayList<Leisure> leisureList = new ArrayList<>();
        String csvClassPath = "classpath:static/csv/leisure.csv";

        List<List<String>> list = csvTemplate.getOpenData(csvClassPath);

        if(list==null) return null;

        for(int i=0;i<list.size();i++){
            Leisure leisure = new Leisure();
            leisure.setEmployeeId(Integer.parseInt(list.get(i).get(0)));
            leisure.setHobby(list.get(i).get(1));
            leisureList.add(leisure);
        }

        return leisureList;
    }
}
