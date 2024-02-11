package com.hourglass.schedule.mapper;

import com.hourglass.schedule.entity.Stop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StopMapper {
    Integer insert(Stop stop);

    Stop selectByTrainCodeAndIndex(String trainCode, Integer stopIndex);

    Integer updateSelectiveByTrainCodeAndIndex(Stop stop);

    List<Stop> selectByTrainCodeOrderByIndexDesc(String trainCode);

    Integer updateIndexByTrainCodeAndStationName(String trainCode, String stationName, Integer stopIndex);
}
