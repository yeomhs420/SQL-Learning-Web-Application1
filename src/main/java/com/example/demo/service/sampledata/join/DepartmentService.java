package com.example.demo.service.sampledata.join;

import com.example.demo.entity.sampledata.join.Department;
import com.example.demo.template.CsvTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    CsvTemplate csvTemplate;

    public List<Department> getAllData() {

        ArrayList<Department> departmentList = new ArrayList<>();
        String csvClassPath = "classpath:static/csv/department.csv";

        List<List<String>> list = csvTemplate.getOpenData(csvClassPath);

        if(list==null) return null;

        for(int i=0;i<list.size();i++){
            Department department = new Department();
            department.setDivision(list.get(i).get(0));
            department.setCode(list.get(i).get(1));
            departmentList.add(department);
        }

        return departmentList;
    }
}
