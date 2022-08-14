package com.example.demo.controller;

import com.example.demo.sampleobject.AgriFoodInfo;
import com.example.demo.sampleobject.CovidInfectionStatus;
import com.example.demo.sampleobject.CovidVaccinationCenter;
import com.example.demo.service.getsampledata.AgriFoodInfoService;
import com.example.demo.service.getsampledata.CovidInfectionStatusesService;
import com.example.demo.service.getsampledata.CovidVaccinationCentersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Controller
public class HomeController {

    @PersistenceContext
    EntityManager em;

    @Autowired
    CovidVaccinationCentersService covidVaccinationCentersService;

    @Autowired
    CovidInfectionStatusesService covidInfectionStatusesService;

    @Autowired
    AgriFoodInfoService agriFoodInfoService;

    @Transactional
    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        List<CovidVaccinationCenter> covidVaccinationCenterList = null;
        List<CovidInfectionStatus> covidInfectionStatusList = null;
        List<AgriFoodInfo> agriFoodInfoList = null;
        try {
            covidVaccinationCenterList = covidVaccinationCentersService.getCovidVaccinationCenters(1, 100);
            covidInfectionStatusList = covidInfectionStatusesService.getCovidInfectionStatuses(1, 31, "20210701", "20210731");
            agriFoodInfoList = agriFoodInfoService.getAgriFoodInfo(1,100);
        } catch (Exception e) {e.printStackTrace();}

        for(int i=0;i<covidVaccinationCenterList.size();i++) em.persist(covidVaccinationCenterList.get(i));
        for(int i=0;i<covidInfectionStatusList.size();i++) em.persist(covidInfectionStatusList.get(i));
        for(int i=0;i<agriFoodInfoList.size();i++) em.persist(agriFoodInfoList.get(i));

        return "outputobj";
    }
}
