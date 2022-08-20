package com.example.demo.service.getsampledata;

import com.example.demo.entity.sampledata.CovidVaccinationCenter;
import com.example.demo.template.MyRestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class CovidVaccinationCentersService {

    @Value("${jooyeok.data.serviceKey}")
    private String serviceKey;

    @Autowired
    MyRestTemplate myRestTemplate;

    public List<CovidVaccinationCenter> getCovidVaccinationCenters(int page, int perPage) {
        ArrayList<CovidVaccinationCenter> covidVaccinationCenterList = new ArrayList<>();
        String requestUrl="https://api.odcloud.kr/api/15077603/v1/uddi:1b3d37ab-8012-4b60-94b1-fc89ce89d1e2";
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(requestUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("page", page)
                .queryParam("perPage", perPage);
        String jsonData = myRestTemplate.getOpenData(MediaType.APPLICATION_JSON, uriComponentsBuilder, HttpMethod.GET);
        if(jsonData=="") return null;

        JSONObject covidVaccinationCentersJsonObject = new JSONObject(jsonData); //JSON String -> JSON Object
        JSONArray dataJsonArray = (JSONArray) covidVaccinationCentersJsonObject.get("data");
        for(int i=0; i<dataJsonArray.length(); i++){
            JSONObject center = (JSONObject) dataJsonArray.get(i);
            CovidVaccinationCenter covidVaccinationCenter = new CovidVaccinationCenter();
            covidVaccinationCenter.setId(Integer.parseInt(center.get("연번").toString()));
            covidVaccinationCenter.setName(center.get("센터명").toString());
            covidVaccinationCenter.setPhone(center.get("사무실전화번호").toString());
            covidVaccinationCenter.setAddress(center.get("주소").toString());
            covidVaccinationCenter.setPostalCode(center.get("우편번호").toString());
            covidVaccinationCenterList.add(covidVaccinationCenter);
        }
        return covidVaccinationCenterList;
    }
}
