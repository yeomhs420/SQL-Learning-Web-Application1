package com.example.demo.service.getsampledata;

import com.example.demo.entity.sampledata.CovidInfectionStatus;
import com.example.demo.template.MyRestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CovidInfectionStatusesService {

    @Value("${jooyeok.data.serviceKey}")
    private String serviceKey;

    @Autowired
    MyRestTemplate myRestTemplate;

    public List<CovidInfectionStatus> getCovidInfectionStatuses(int pageNo, int numOfRows, String startCreateDt, String endCreateDt) {
        ArrayList<CovidInfectionStatus> covidInfectionStatusList = new ArrayList<>();
        String requestUrl="http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson";
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(requestUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", numOfRows)
                .queryParam("startCreateDt", startCreateDt)
                .queryParam("endCreateDt", endCreateDt);
        String jsonData = myRestTemplate.getOpenData(MediaType.APPLICATION_JSON, uriComponentsBuilder, HttpMethod.GET);
        if(jsonData=="") return null;

        JSONObject covidInfectionStatusesJsonObject = new JSONObject(jsonData); //JSON String -> JSON Object
        JSONObject response = (JSONObject)covidInfectionStatusesJsonObject.get("response");
        JSONObject body = (JSONObject)response.get("body");
        JSONObject items = (JSONObject)body.get("items");
        JSONArray itemArr = (JSONArray)items.get("item");

        for(int i=0; i<itemArr.length(); i++){
            JSONObject status = (JSONObject) itemArr.get(i);
            CovidInfectionStatus covidInfectionStatus = new CovidInfectionStatus();
            covidInfectionStatus.setDataNum(i+1);
            covidInfectionStatus.setDate(dateConverter(status.get("stateDt").toString()));
            covidInfectionStatus.setAccExamCnt(Integer.parseInt(status.get("accExamCnt").toString()));
            covidInfectionStatus.setAccDecideCnt(Integer.parseInt(status.get("decideCnt").toString()));
            covidInfectionStatus.setAccDeathCnt(Integer.parseInt(status.get("deathCnt").toString()));
            covidInfectionStatusList.add(covidInfectionStatus);
        }
        return covidInfectionStatusList;
    }

    public Date dateConverter(String dateStr) {
        Date date=null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
