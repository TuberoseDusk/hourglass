package com.hourglass.schedule.service;

import cn.hutool.core.bean.BeanUtil;
import com.hourglass.schedule.entity.Seat;
import com.hourglass.schedule.mapper.SeatMapper;
import com.hourglass.schedule.response.SeatQueryResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {
    @Resource
    private SeatMapper seatMapper;

    public List<SeatQueryResponse> query(Long dailyTrainId) {
        List<Seat> seats = seatMapper.selectByDailyTrainId(dailyTrainId);
        List<SeatQueryResponse> seatQueryResponses = new ArrayList<>(seats.size());
        for (Seat seat : seats) {
            seatQueryResponses.add(BeanUtil.copyProperties(seat, SeatQueryResponse.class));
        }
        return seatQueryResponses;
    }
}
