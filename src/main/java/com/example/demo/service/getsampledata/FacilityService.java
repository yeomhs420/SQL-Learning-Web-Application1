package com.example.demo.service.getsampledata;

import com.example.demo.sampleobject.AgriFoodInfo;
import com.example.demo.template.MyRestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacilityService {
    @Value("${hyeongseob.data.serviceKey}")
    private String serviceKey;

    @Autowired
    MyRestTemplate myRestTemplate;

    public void getData(int Page_No, int Page_Size) {
        System.out.println("servicekey = " + serviceKey);

        String requestUrl="http://apis.data.go.kr/B554390/FmsFacil/stat_ar";


        URI uri = UriComponentsBuilder.fromUriString(requestUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("type", "json")
                .queryParam("pageNo", Page_No)
                .queryParam("numOfRows", Page_Size)
                .build()
                .encode()
                .toUri();


        System.out.println(uri);

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                String.class
        );


    }
}
