package com.example.demo.service;

import com.example.demo.entity.sampledata.*;
import com.example.demo.entity.sampledata.join.*;
import com.example.demo.jpa.repository.*;
import com.example.demo.jpa.repository.join.*;
import com.example.demo.service.sampledata.*;
import com.example.demo.service.sampledata.join.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaveDBService {

    @Autowired
    CovidVaccinationCenterService covidVaccinationCenterService;
    @Autowired CovidInfectionStatusService covidInfectionStatusService;
    @Autowired TestStatusByEventService testStatusByEventService;
    @Autowired RegionalRecoveryService regionalRecoveryService;
    @Autowired EducationCostService educationCostService;
    @Autowired
    EmployeeService employeeService;
    @Autowired AssignmentService assignmentService;
    @Autowired DepartmentService departmentService;
    @Autowired LeisureService leisureService;
    @Autowired MenteeMentoService menteeMentoService;

    @Autowired CovidInfectionStatusRepository covidInfectionStatusRepository;
    @Autowired CovidVaccinationCenterRepository covidVaccinationCenterRepository;
    @Autowired EducationCostRepository educationCostRepository;
    @Autowired RegionalRecoveryCostInfoRepository regionalRecoveryCostInfoRepository;
    @Autowired TestStatusByEventRepository testStatusByEventRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired AssignmentRepository assignmentRepository;
    @Autowired DepartmentRepository departmentRepository;
    @Autowired LeisureRepository leisureRepository;
    @Autowired MenteeMentoRepository menteeMentoRepository;


    @Transactional("userTransactionManager")
    public void saveAllSampleData() {
        List<CovidVaccinationCenter> covidVaccinationCenterList = null;
        List<CovidInfectionStatus> covidInfectionStatusList = null;
        List<TestStatusByEvent> testStatusByEventList = null;
        List<RegionalRecoveryCostInfo> regionalRecoveryList = null;
        List<EducationCost> educationCostList = null;
        List<Employee> employeeList = null;
        List<Assignment> assignmentList = null;
        List<Department> departmentList = null;
        List<Leisure> leisureList = null;
        List<MenteeMento> menteeMentoList = null;

        try {
            covidVaccinationCenterList = covidVaccinationCenterService.getAllData(1, 100);
            covidInfectionStatusList = covidInfectionStatusService.getAllData(1, 31, "20210701", "20210731");
            testStatusByEventList = testStatusByEventService.getAllData();
            regionalRecoveryList = regionalRecoveryService.getAllData();
            educationCostList = educationCostService.getAllData();
            employeeList = employeeService.getAllData();
            assignmentList = assignmentService.getAllData();
            departmentList = departmentService.getAllData();
            leisureList = leisureService.getAllData();
            menteeMentoList = menteeMentoService.getAllData();
        } catch (Exception e) {e.printStackTrace();}

        for(int i=0;i<covidVaccinationCenterList.size();i++) covidVaccinationCenterRepository.save(covidVaccinationCenterList.get(i));
        for(int i=0;i<covidInfectionStatusList.size();i++) covidInfectionStatusRepository.save(covidInfectionStatusList.get(i));
        for(int i=0;i<testStatusByEventList.size();i++) testStatusByEventRepository.save(testStatusByEventList.get(i));
        for(int i=0;i<regionalRecoveryList.size();i++) regionalRecoveryCostInfoRepository.save(regionalRecoveryList.get(i));
        for(int i=0;i<educationCostList.size();i++) educationCostRepository.save(educationCostList.get(i));
        for(int i = 0; i< employeeList.size(); i++) employeeRepository.save(employeeList.get(i));
        for(int i=0;i<assignmentList.size();i++) assignmentRepository.save(assignmentList.get(i));
        for(int i=0;i<departmentList.size();i++) departmentRepository.save(departmentList.get(i));
        for(int i=0;i<leisureList.size();i++) leisureRepository.save(leisureList.get(i));
        for(int i=0;i<menteeMentoList.size();i++) menteeMentoRepository.save(menteeMentoList.get(i));
    }
}
