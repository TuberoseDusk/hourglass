package com.hourglass.schedule.service;

import cn.hutool.core.bean.BeanUtil;
import com.hourglass.common.enums.ResponseEnum;
import com.hourglass.common.exception.BusinessException;
import com.hourglass.schedule.entity.Station;
import com.hourglass.schedule.mapper.StationMapper;
import com.hourglass.schedule.request.StationCreateRequest;
import com.hourglass.schedule.response.StationQueryResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class StationService {
    @Resource
    private StationMapper stationMapper;


    /**
     *创建新的车站
     */
    public void create(StationCreateRequest stationCreateRequest) {
        if (stationMapper.selectByName(stationCreateRequest.getStationName()) != null) {
            throw new BusinessException(ResponseEnum.STATION_NAME_EXIST);
        }
        if (stationCreateRequest.getStationState() == null) {
            stationCreateRequest.setStationState(0);
        }
        stationMapper.insert(BeanUtil.copyProperties(stationCreateRequest, Station.class));
    }
}
