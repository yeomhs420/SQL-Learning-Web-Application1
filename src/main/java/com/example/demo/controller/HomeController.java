package com.example.demo.controller;

import com.example.demo.sampleobject.CovidVaccinationCenter;
import com.example.demo.service.SampleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    SampleDataService sampleDataService;

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        List<CovidVaccinationCenter> covidVaccinationCenterList = sampleDataService.getCovidVaccinationCenters(1, 100);
        for(int i=0;i<covidVaccinationCenterList.size();i++){
            CovidVaccinationCenter covidVaccinationCenter = covidVaccinationCenterList.get(i);
            System.out.println("--------------------");
            System.out.println("id: "+covidVaccinationCenter.getId());
            System.out.println("name: "+covidVaccinationCenter.getName());
            System.out.println("phone: "+covidVaccinationCenter.getPhone());
            System.out.println("address: "+covidVaccinationCenter.getAddress());
            System.out.println("postalCode: "+covidVaccinationCenter.getPostalCode());
            System.out.println("--------------------");
        }
        return "outputobj";
    }
}
