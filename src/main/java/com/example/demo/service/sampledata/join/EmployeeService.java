package com.example.demo.service.sampledata.join;

import com.example.demo.entity.sampledata.join.Employee;
import com.example.demo.template.CsvTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    CsvTemplate csvTemplate;

    public List<Employee> getAllData() {

        ArrayList<Employee> employeeList = new ArrayList<>();
        String csvClassPath = "classpath:static/csv/employee.csv";

        List<List<String>> list = csvTemplate.getOpenData(csvClassPath);

        if(list==null) return null;

        for(int i=0;i<list.size();i++){
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(list.get(i).get(0)));
            employee.setName(list.get(i).get(1));
            employee.setPosition(list.get(i).get(2));
            employee.setDepartment(list.get(i).get(3));
            employee.setSalary(Integer.parseInt(list.get(i).get(4)));
            employee.setAge(Integer.parseInt(list.get(i).get(5)));
            employee.setPhone(list.get(i).get(6));
            employee.setLeave(list.get(i).get(7));
            employeeList.add(employee);
        }

        return employeeList;
    }
}
