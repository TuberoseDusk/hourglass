package com.hourglass.schedule.mapper;

import com.hourglass.schedule.entity.Carriage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CarriageMapper {
    Integer insert(Carriage carriage);

    Carriage selectByType(Integer carriageType);
}
