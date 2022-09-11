package com.example.demo.mapper;

import com.example.demo.entity.sampledata.CovidVaccinationCenter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestMapper {
    @Select("${sql}")
    List<Map<String, Object>> getResult(@Param("sql") String sql);
}
