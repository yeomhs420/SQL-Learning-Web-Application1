package com.example.demo.service.sampledata;

import com.example.demo.entity.sampledata.AgriFoodInfo;
import com.example.demo.template.MyRestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgriFoodInfoService {

    @Value("${chanmyeong.data.serviceKey}")
    private String serviceKey;

    @Autowired
    MyRestTemplate myRestTemplate;

    public List<AgriFoodInfo> getAgriFoodInfo(int Page_No, int Page_Size) {
        ArrayList<AgriFoodInfo> AgriFoodInfoList = new ArrayList<>();
        String requestUrl="http://apis.data.go.kr/1390802/AgriFood/MzenFoodCode/getKoreanFoodList";
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(requestUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("Page_No", Page_No)
                .queryParam("Page_Size", Page_Size);
        String jsonData = myRestTemplate.getOpenData(MediaType.APPLICATION_XML, uriComponentsBuilder, HttpMethod.GET);
        if(jsonData=="") return null;

        JSONObject agriFoodInfoJsonObject = XML.toJSONObject(jsonData);
        JSONObject response = (JSONObject)agriFoodInfoJsonObject.get("response");
        JSONObject body = (JSONObject)response.get("body");
        JSONObject items = (JSONObject)body.get("items");
        JSONArray item = (JSONArray)items.get("item");

        for(int i=0; i<item.length(); i++){
            JSONObject status = (JSONObject) item.get(i);
            AgriFoodInfo agriFoodInfo = new AgriFoodInfo();
            agriFoodInfo.setFood_Code(status.get("food_Code").toString());
            agriFoodInfo.setLarge_Name(status.get("large_Name").toString());
            agriFoodInfo.setMiddle_Name(status.get("middle_Name").toString());
            agriFoodInfo.setFood_Name(status.get("food_Name").toString());
            agriFoodInfo.setFood_Volume(Double.parseDouble(status.get("food_Volume").toString()));
            AgriFoodInfoList.add(agriFoodInfo);
        }
        return AgriFoodInfoList;
    }
}
