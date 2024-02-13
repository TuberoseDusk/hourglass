package com.hourglass.schedule.mapper;

import com.hourglass.schedule.entity.Seat;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SeatMapper {
    Integer insert(Seat seat);

    Integer insertBatch(List<Seat> seatList);

    List<Seat> selectByDailyTrainId(Long dailyTrainId);
}
