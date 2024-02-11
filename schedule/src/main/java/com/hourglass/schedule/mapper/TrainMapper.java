package com.hourglass.schedule.mapper;

import com.hourglass.schedule.entity.Train;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TrainMapper {
    Integer insert(Train train);

    Train selectByTrainCode(String trainCode);

    Integer updateCarriagesByTrainCode(String trainCode, String carriages);

    List<Train> selectAll();
}
