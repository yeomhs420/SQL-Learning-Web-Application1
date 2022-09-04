package com.example.demo.service.getsampledata;


import com.example.demo.entity.sampledata.EducationCost;
import com.example.demo.template.CsvTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EducationCostService {

    @Autowired
    CsvTemplate csvTemplate;

    public List<EducationCost> getEducationCost(){

        ArrayList<EducationCost> educationCostList = new ArrayList<>();
        String csvClassPath = "classpath:static/csv/education_cost.csv";
        List<List<String>> list = csvTemplate.getOpenData(csvClassPath);

        if(list==null) return null;

        for(int j = 0;j<list.size();j++){
            EducationCost educationCost = new EducationCost();

            educationCost.setSubject(list.get(j).get(0).replace("\"", ""));
            educationCost.setTotalcost(Integer.parseInt(list.get(j).get(1)));
            educationCost.setFirstgrade(Integer.parseInt(list.get(j).get(2)));
            educationCost.setSecondgrade(Integer.parseInt(list.get(j).get(3)));
            educationCost.setThirdgrade(Integer.parseInt(list.get(j).get(4)));

            educationCostList.add(educationCost);
        }

        return educationCostList;
    }

}
