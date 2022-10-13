package com.example.demo.example;

import com.example.demo.entity.sampledata.CovidInfectionStatus;
import com.example.demo.entity.sampledata.CovidVaccinationCenter;
import com.example.demo.jpa.repository.CovidInfectionStatusRepository;
import com.example.demo.jpa.repository.CovidVaccinationCenterRepository;
import com.example.demo.service.sampledata.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class JpaRepositoryExample {

    @Autowired
    CovidInfectionStatusService covidInfectionStatusService;
    @Autowired CovidInfectionStatusRepository covidInfectionStatusRepository;
    @Autowired CovidVaccinationCenterRepository covidVaccinationCenterRepository;

    public void execute(){
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
                covidInfectionStatusService.dateConverter("20210710"), covidInfectionStatusService.dateConverter("20210720")
        );
        for(int i=0;i<statuses.size();i++) System.out.println(statuses.get(i).getAccDeathCnt());
    }
}
