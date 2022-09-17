package com.example.demo.example;

import com.example.demo.entity.sampledata.AgriFoodInfo;
import com.example.demo.entity.sampledata.CovidVaccinationCenter;
import com.example.demo.jpa.repository.AgriFoodInfoRepository;
import com.example.demo.jpa.repository.CovidInfectionStatusRepository;
import com.example.demo.jpa.repository.CovidVaccinationCenterRepository;
import com.example.demo.service.sampledata.AgriFoodInfoService;
import com.example.demo.service.sampledata.CovidInfectionStatusesService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class AgriFoodInfo_JpaRepoExample {
    @PersistenceContext(unitName = "sampleData")
    EntityManager em;

    @Autowired
    AgriFoodInfoService agriFoodInfoService;
    @Autowired
    AgriFoodInfoRepository agriFoodInfoRepository;

    public void execute() {
        // 예시 1 : 음식 이름에 갈비가 들어가는 모든 데이터 조회
        System.out.println();
        System.out.println("예시 1");
        List<AgriFoodInfo> galbiList = agriFoodInfoRepository.findByFood_NameLike("%갈비%");
        for(int i=0;i<galbiList.size();i++) System.out.println(galbiList.get(i).getFood_Name());

        // 예시 2 : 음식 코드가 알파벳 C로 시작하는 상위 데이터 10개 조회
        System.out.println();
        System.out.println("예시 2");
        List<AgriFoodInfo> codeList = agriFoodInfoRepository.findByFood_CodeStartingWith("C");
        for(int i=0;i<codeList.size();i++) System.out.println(codeList.get(i).getFood_Name());

        // 예시 3 : 식품 중량이 300~500 사이인 모든 데이터 조회
        System.out.println();
        System.out.println("예시 3");
        List<AgriFoodInfo> volumeList = agriFoodInfoRepository.findByFood_VolumeBetween(300, 500);
        for(int i=0;i<volumeList.size();i++) System.out.println(volumeList.get(i).getFood_Name());
    }
}
