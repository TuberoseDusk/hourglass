package com.hourglass.schedule.mapper;

import com.hourglass.schedule.entity.Station;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StationMapper {
    Integer insert(Station station);

    Station selectByName(String stationName);
}
