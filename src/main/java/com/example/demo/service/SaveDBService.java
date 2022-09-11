package com.example.demo.service;

import com.example.demo.entity.sampledata.*;
import com.example.demo.jpa.repository.CovidInfectionStatusRepository;
import com.example.demo.jpa.repository.CovidVaccinationCenterRepository;
import com.example.demo.service.sampledata.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class SaveDBService {

    @PersistenceContext EntityManager em;

    @Autowired
    CovidVaccinationCentersService covidVaccinationCentersService;
    @Autowired
    CovidInfectionStatusesService covidInfectionStatusesService;
    @Autowired
    AgriFoodInfoService agriFoodInfoService;
    @Autowired
    TestStatusByEventService testStatusByEventService;
    @Autowired
    RegionalRecoveryService regionalRecoveryService;
    @Autowired EducationCostService educationCostService;
    @Autowired CovidInfectionStatusRepository covidInfectionStatusRepository;
    @Autowired CovidVaccinationCenterRepository covidVaccinationCenterRepository;


    @Transactional
    public void saveAllSampleData() {
        List<CovidVaccinationCenter> covidVaccinationCenterList = null;
        List<CovidInfectionStatus> covidInfectionStatusList = null;
        List<AgriFoodInfo> agriFoodInfoList = null;
        List<TestStatusByEvent> testStatusByEventList = null;
        List<RegionalRecoveryCostInfo> regionalRecoveryList = null;
        List<EducationCost> educationCostList = null;
        try {
            covidVaccinationCenterList = covidVaccinationCentersService.getCovidVaccinationCenters(1, 100);
            covidInfectionStatusList = covidInfectionStatusesService.getCovidInfectionStatuses(1, 31, "20210701", "20210731");
            agriFoodInfoList = agriFoodInfoService.getAgriFoodInfo(1,100);
            testStatusByEventList = testStatusByEventService.getTestStatusByEvent();
            regionalRecoveryList = regionalRecoveryService.getRecoveryCost();
            educationCostList = educationCostService.getEducationCost();

        } catch (Exception e) {e.printStackTrace();}

        for(int i=0;i<covidVaccinationCenterList.size();i++) covidVaccinationCenterRepository.save(covidVaccinationCenterList.get(i));
        for(int i=0;i<covidInfectionStatusList.size();i++) covidInfectionStatusRepository.save(covidInfectionStatusList.get(i));
        for(int i=0;i<agriFoodInfoList.size();i++) em.persist(agriFoodInfoList.get(i));
        for(int i=0;i<testStatusByEventList.size();i++) em.persist(testStatusByEventList.get(i));
        for(int i=0;i<regionalRecoveryList.size();i++) em.persist(regionalRecoveryList.get(i));
        for(int i=0;i<educationCostList.size();i++) em.persist(educationCostList.get(i));
    }
}
