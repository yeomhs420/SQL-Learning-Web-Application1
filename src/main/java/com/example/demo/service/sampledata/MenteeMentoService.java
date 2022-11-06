package com.example.demo.service.sampledata;

import com.example.demo.entity.sampledata.MenteeMento;
import com.example.demo.template.CsvTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenteeMentoService {

    @Autowired
    CsvTemplate csvTemplate;

    public List<MenteeMento> getAllData() {

        ArrayList<MenteeMento> menteeMentoList = new ArrayList<>();
        String csvClassPath = "classpath:static/csv/mentee_mento.csv";

        List<List<String>> list = csvTemplate.getOpenData(csvClassPath);

        if(list==null) return null;

        for(int i=0;i<list.size();i++){
            MenteeMento menteeMento = new MenteeMento();
            menteeMento.setStudentId(Integer.parseInt(list.get(i).get(0)));
            menteeMento.setStudentName(list.get(i).get(1));
            menteeMento.setMentoId(Integer.parseInt(list.get(i).get(2)));
            menteeMentoList.add(menteeMento);
        }

        return menteeMentoList;
    }
}
