package com.hourglass.schedule.mapper;

import com.hourglass.schedule.entity.DailyTrain;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;

@Mapper
public interface DailyTrainMapper {
    Integer insert(DailyTrain dailyTrain);

    DailyTrain selectByStartDateAndTrainCode(LocalDate startDate, String trainCode);

    DailyTrain selectByDailyTrainId(Long dailyTrainId);

}
