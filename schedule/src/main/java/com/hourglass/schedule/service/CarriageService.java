package com.hourglass.schedule.service;

import cn.hutool.core.bean.BeanUtil;
import com.hourglass.common.enums.ResponseEnum;
import com.hourglass.common.exception.BusinessException;
import com.hourglass.schedule.entity.Carriage;
import com.hourglass.schedule.mapper.CarriageMapper;
import com.hourglass.schedule.request.CarriageCreateRequest;
import com.hourglass.schedule.response.CarriageQueryResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CarriageService {
    @Resource
    private CarriageMapper carriageMapper;

    /**
     * 新增车厢类型
     */
    public void create(CarriageCreateRequest carriageCreateRequest) {

        if (carriageCreateRequest.getCarriageType() == null ||
                carriageCreateRequest.getRowCount() == null ||
                carriageCreateRequest.getColumnCount() == null) {
            throw new BusinessException(ResponseEnum.REQUEST_PARAM_ERROR);
        }

        Carriage carriage = carriageMapper.selectByType(carriageCreateRequest.getCarriageType());
        if (carriage != null) {
            throw new BusinessException(ResponseEnum.CARRIAGE_TYPE_EXIST);
        }

        carriage = BeanUtil.copyProperties(carriageCreateRequest, Carriage.class);
        carriageMapper.insert(carriage);
    }


    /**
     * 查询车厢类型
     */
    public CarriageQueryResponse query(Integer carriageType) {
        Carriage carriage = carriageMapper.selectByType(carriageType);
        if (carriage == null) {
            return null;
        }
        return BeanUtil.copyProperties(carriage, CarriageQueryResponse.class);
    }

}
