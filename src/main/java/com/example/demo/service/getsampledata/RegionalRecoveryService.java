package com.example.demo.service.getsampledata;

import com.example.demo.entity.sampledata.RegionalRecoveryCostInfo;
import com.example.demo.template.CsvTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionalRecoveryService {
    @Autowired
    CsvTemplate csvTemplate;

    public List<RegionalRecoveryCostInfo> getRecoveryCost() {

        ArrayList<RegionalRecoveryCostInfo> recoveryCostList = new ArrayList<>();
        String csvClassPath = "classpath:static/csv/regional_recovery_cost.csv";
        List<List<String>> list = csvTemplate.getOpenData(csvClassPath);

        if(list==null) return null;

        for(int i=0;i<list.size();i++){
            RegionalRecoveryCostInfo recoveryCostInfo = new RegionalRecoveryCostInfo();

            recoveryCostInfo.setRegion(list.get(i).get(0).replace("\"", ""));
            recoveryCostInfo.setTotalCost(Integer.parseInt(list.get(i).get(1)));
            recoveryCostInfo.setSupportCost(Integer.parseInt(list.get(i).get(2)));  // TreasuryCost + ProvincialCost
            recoveryCostInfo.setTreasuryCost(Integer.parseInt(list.get(i).get(3)));
            recoveryCostInfo.setProvincialCost(Integer.parseInt(list.get(i).get(4)));
            recoveryCostInfo.setSelfCost(Integer.parseInt(list.get(i).get(5)));

            recoveryCostList.add(recoveryCostInfo);

        }

        return recoveryCostList;
    }
}
