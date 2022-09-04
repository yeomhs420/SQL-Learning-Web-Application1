package com.example.demo.controller;

import com.example.demo.entity.sampledata.*;
import com.example.demo.jpa.repository.CovidInfectionStatusRepository;
import com.example.demo.jpa.repository.CovidVaccinationCenterRepository;
import com.example.demo.service.getsampledata.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.naming.directory.SearchResult;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

@Controller
public class HomeController {

    @PersistenceContext(unitName = "sampleData") EntityManager em;

    @Autowired CovidVaccinationCentersService covidVaccinationCentersService;
    @Autowired CovidInfectionStatusesService covidInfectionStatusesService;
    @Autowired AgriFoodInfoService agriFoodInfoService;
    @Autowired TestStatusByEventService testStatusByEventService;
    @Autowired RegionalRecoveryService regionalRecoveryService;
    @Autowired EducationCostService educationCostService;
    @Autowired CovidInfectionStatusRepository covidInfectionStatusRepository;
    @Autowired CovidVaccinationCenterRepository covidVaccinationCenterRepository;


    @Transactional("transactionManager")
    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
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
            testStatusByEventList = testStatusByEventService.getTestStatusByEvent(); // 파일을 그냥 가져오면 되므로 파라미터를 명시할 필요가 없음
            regionalRecoveryList = regionalRecoveryService.getRecoveryCost();
            educationCostList = educationCostService.getEducationCost();

        } catch (Exception e) {e.printStackTrace();}

        for(int i=0;i<covidVaccinationCenterList.size();i++) covidVaccinationCenterRepository.save(covidVaccinationCenterList.get(i));
        for(int i=0;i<covidInfectionStatusList.size();i++) covidInfectionStatusRepository.save(covidInfectionStatusList.get(i));
        for(int i=0;i<agriFoodInfoList.size();i++) em.persist(agriFoodInfoList.get(i));
        for(int i=0;i<testStatusByEventList.size();i++) em.persist(testStatusByEventList.get(i));
        for(int i=0;i<regionalRecoveryList.size();i++) em.persist(regionalRecoveryList.get(i));
        for(int i=0;i<educationCostList.size();i++) em.persist(educationCostList.get(i));


        // 예시 1 : 서울에 있는 모든 코로나 백신 센터
        System.out.println();
        System.out.println("예시 1");
        List<CovidVaccinationCenter> centers = covidVaccinationCenterRepository.findByAddressLike("서울%");
        for(int i=0;i<centers.size();i++) System.out.println(centers.get(i).getAddress());


        // 예시 2 : 코로나 백신 센터의 전화번호 정보가 없는(null) 센터 이름
        System.out.println();
        System.out.println("예시 2");
        List<CovidVaccinationCenter> noPhoneCenters = covidVaccinationCenterRepository.findByPhoneIsNull();
        for(int i=0;i<noPhoneCenters.size();i++) System.out.println(noPhoneCenters.get(i).getName());


        // 예시 3 : 코로나 백신 센터의 주소를 기준으로 오름차순 정렬
        System.out.println();
        System.out.println("예시 3");
        List<CovidVaccinationCenter> centersAsc = covidVaccinationCenterRepository.findAll(Sort.by("address").ascending());
        for(int i=0;i<centersAsc.size();i++) System.out.println(centersAsc.get(i).getAddress());


        // 예시 4 : 코로나 백신 센터의 주소를 기준으로 오름차순 정렬 결과 상위 10개
        System.out.println();
        System.out.println("예시 4");
        Page<CovidVaccinationCenter> centersAsc10 = covidVaccinationCenterRepository.findAll(PageRequest.of(0, 10, Sort.by("address").ascending()));
        System.out.println(centersAsc10.getSize());
        Iterator iterator = centersAsc10.iterator();
        while(iterator.hasNext()) {
            CovidVaccinationCenter next = (CovidVaccinationCenter) iterator.next();
            System.out.println(next.getAddress());
        }

        // 예시 5 : 2021년 7월 10일부터 2021년 7월 20일까지의 모든 누적 사망자 수
        System.out.println();
        System.out.println("예시 5");
        List<CovidInfectionStatus> statuses = covidInfectionStatusRepository.findByDateBetween(
                covidInfectionStatusesService.dateConverter("20210710"), covidInfectionStatusesService.dateConverter("20210720")
        );
        for(int i=0;i<statuses.size();i++) System.out.println(statuses.get(i).getAccDeathCnt());

        return "outputobj";
    }


}
