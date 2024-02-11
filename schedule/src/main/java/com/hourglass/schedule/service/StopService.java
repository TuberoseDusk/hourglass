package com.hourglass.schedule.service;

import cn.hutool.core.bean.BeanUtil;
import com.hourglass.common.enums.ResponseEnum;
import com.hourglass.common.exception.BusinessException;
import com.hourglass.schedule.entity.Stop;
import com.hourglass.schedule.mapper.StopMapper;
import com.hourglass.schedule.request.StopAddRequest;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StopService {
    @Resource
    private StopMapper stopMapper;

    /**
     * 添加车站信息
     */
    @Transactional
    public void add(StopAddRequest stopAddRequest) {
        List<Stop> stops = stopMapper.selectByTrainCodeOrderByIndexDesc(stopAddRequest.getTrainCode());
        // 没有始发站和终点站信息
        if (stops == null || stops.isEmpty()) {
            throw new BusinessException(ResponseEnum.TRAIN_CODE_NOT_EXIST);
        }

        // 在终点站之后设置停靠站
        if (stopAddRequest.getStopIndex() >= stops.size()) {
            throw new BusinessException(ResponseEnum.ILLEGAL_STOP_INDEX);
        }

        Stop stop = BeanUtil.copyProperties(stopAddRequest, Stop.class);
        // 更新新站点之后的站点序号
        for (Stop existStop : stops) {
            if (stop.getStopIndex() <= existStop.getStopIndex()) {
                stopMapper.updateIndexByTrainCodeAndStationName(existStop.getTrainCode(),
                        existStop.getStationName(), existStop.getStopIndex() + 1);
            }
        }
        stopMapper.insert(stop);
    }
}
