package com.example.demo.service.sampledata.join;

import com.example.demo.entity.sampledata.join.Assignment;
import com.example.demo.template.CsvTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssignmentService {

    @Autowired CsvTemplate csvTemplate;

    public List<Assignment> getAllData() {

        ArrayList<Assignment> assignmentList = new ArrayList<>();
        String csvClassPath = "classpath:static/csv/assignment.csv";

        List<List<String>> list = csvTemplate.getOpenData(csvClassPath);

        if(list==null) return null;

        for(int i=0;i<list.size();i++){
            Assignment assignment = new Assignment();
            assignment.setWork(list.get(i).get(0));
            assignment.setEmployeeId(Integer.parseInt(list.get(i).get(1)));
            assignmentList.add(assignment);
        }

        return assignmentList;
    }
}
