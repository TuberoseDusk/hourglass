package com.hourglass.schedule.service;

import cn.hutool.core.bean.BeanUtil;
import com.hourglass.common.enums.ResponseEnum;
import com.hourglass.common.exception.BusinessException;
import com.hourglass.schedule.entity.Carriage;
import com.hourglass.schedule.entity.Stop;
import com.hourglass.schedule.entity.Train;
import com.hourglass.schedule.mapper.CarriageMapper;
import com.hourglass.schedule.mapper.StationMapper;
import com.hourglass.schedule.mapper.StopMapper;
import com.hourglass.schedule.mapper.TrainMapper;
import com.hourglass.schedule.request.CarriageAddRequest;
import com.hourglass.schedule.request.TrainCreateRequest;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrainService {
    @Resource
    private TrainMapper trainMapper;

    @Resource
    private StationMapper stationMapper;

    @Resource
    private StopMapper stopMapper;

    @Resource
    private CarriageMapper carriageMapper;

    @Transactional
    public void create(TrainCreateRequest trainCreateRequest) {
        // 检查始发站和终点站是否存在
        if (stationMapper.selectByName(trainCreateRequest.getStartStation()) == null ||
                stationMapper.selectByName(trainCreateRequest.getEndStation()) == null) {
            throw new BusinessException(ResponseEnum.STATION_NAME_NOT_EXIST);
        }

        // 检查列车号是否重复
        if (trainMapper.selectByTrainCode(trainCreateRequest.getTrainCode()) != null) {
            throw new BusinessException(ResponseEnum.TRAIN_CODE_EXIST);
        }

        trainMapper.insert(BeanUtil.copyProperties(trainCreateRequest, Train.class));

        // 创建始发站和终点站数据
        Stop startStop = new Stop(trainCreateRequest.getTrainCode(),
                0,
                trainCreateRequest.getStartStation(),
                trainCreateRequest.getStartTime(),
                trainCreateRequest.getStartTime());
        stopMapper.insert(startStop);

        Stop endStop = new Stop(trainCreateRequest.getTrainCode(),
                1,
                trainCreateRequest.getEndStation(),
                trainCreateRequest.getEndTime(),
                trainCreateRequest.getEndTime());
        stopMapper.insert(endStop);
    }

    public void addCarriage(CarriageAddRequest carriageAddRequest) {
        Train train = trainMapper.selectByTrainCode(carriageAddRequest.getTrainCode());
        if (train == null) {
            throw new BusinessException(ResponseEnum.TRAIN_CODE_NOT_EXIST);
        }

        // 检查车厢类型
        Carriage carriage = carriageMapper.selectByType(carriageAddRequest.getCarriageType());
        if (carriage == null) {
            throw new BusinessException(ResponseEnum.CARRIAGE_TYPE_NOT_EXIST);
        }
        String carriageMark = carriage.getCarriageType().toString();

        if (train.getCarriages() == null || train.getCarriages().isBlank()) {
            trainMapper.updateCarriagesByTrainCode(train.getTrainCode(), carriageMark);
        } else {
            trainMapper.updateCarriagesByTrainCode(train.getTrainCode(),
                    train.getCarriages() + "-" + carriageMark);
        }
    }
}
