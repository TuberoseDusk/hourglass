package com.hourglass.schedule.mapper;

import com.hourglass.schedule.entity.DailyStop;
import com.hourglass.schedule.entity.Section;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DailyStopMapper {
    Integer insert(DailyStop dailyStop);

    List<Section> selectSection(String startStation, String endStation, LocalDate startDate);
}
