package com.example.demo.template;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvTemplate {

    public List<List<String>> getOpenData(String csvClassPath) {
        List<List<String>> list = new ArrayList<List<String>>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(ResourceUtils.getFile(csvClassPath)));
            String line="";
            while((line=br.readLine())!=null) {
                String[] token = line.split(",");
                List<String> tempList = new ArrayList<String>(Arrays.asList(token));
                list.add(tempList);
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
            return null;
        } catch (IOException ie) {
            ie.printStackTrace();
            return null;
        } finally {
            try {
                if(br!=null) br.close();
            } catch (IOException ie2) {
                ie2.printStackTrace();
                return null;
            }
        }
        return list;
    }
}
