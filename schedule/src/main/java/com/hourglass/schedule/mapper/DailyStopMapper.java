package com.hourglass.schedule.mapper;

import com.hourglass.schedule.entity.DailyStop;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DailyStopMapper {
    Integer insert(DailyStop dailyStop);
}
