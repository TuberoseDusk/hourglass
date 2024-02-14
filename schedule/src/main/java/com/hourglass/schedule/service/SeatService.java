package com.hourglass.schedule.service;

import cn.hutool.core.bean.BeanUtil;
import com.hourglass.schedule.entity.Seat;
import com.hourglass.schedule.mapper.SeatMapper;
import com.hourglass.schedule.request.SeatStateUpdateRequest;
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

    public SeatQueryResponse query(Long dailyTrainId, Integer seatNumber) {
        Seat seat = seatMapper.selectByDailyTrainIdAndSeatNumber(dailyTrainId, seatNumber);
        return BeanUtil.copyProperties(seat, SeatQueryResponse.class);
    }

    public void updateState(SeatStateUpdateRequest seatStateUpdateRequest) {
        Seat seat = seatMapper.selectBySeatId(seatStateUpdateRequest.getSeatId());
        int startStopIndex = seatStateUpdateRequest.getStartStopIndex();
        Integer endStopIndex = seatStateUpdateRequest.getEndStopIndex();
        String seatState = seat.getSeatState();
        String newState = seatState.substring(0, startStopIndex) + seatStateUpdateRequest.getFlag().repeat(endStopIndex - startStopIndex) + seatState.substring(endStopIndex);
        seatMapper.updateSeatState(seat.getSeatId(), newState);
    }
}
