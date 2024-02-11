package com.hourglass.schedule.service;


import com.hourglass.common.enums.ResponseEnum;
import com.hourglass.common.exception.BusinessException;
import com.hourglass.common.util.Snowflake;
import com.hourglass.schedule.entity.*;
import com.hourglass.schedule.mapper.*;
import com.hourglass.schedule.request.DailySeatGenerateRequest;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class DailyTrainService {
    @Resource
    private DailyTrainMapper dailyTrainMapper;

    @Resource
    private DailyStopMapper dailyStopMapper;

    @Resource
    private SeatMapper seatMapper;
    
    @Resource
    private StopMapper stopMapper;

    @Resource
    private TrainMapper trainMapper;

    @Resource
    private CarriageMapper carriageMapper;

    /**
     *按照基础数据创建每日列车数据
     */
    @Transactional
    public void generate(DailySeatGenerateRequest dailySeatGenerateRequest) {
        // 检查列车号是否存在
        Train train = trainMapper.selectByTrainCode(dailySeatGenerateRequest.getTrainCode());
        if (train == null) {
            throw new BusinessException(ResponseEnum.TRAIN_CODE_NOT_EXIST);
        }

        // 新增每日列车数据
        DailyTrain dailyTrain = new DailyTrain();
        dailyTrain.setDailyTrainId(Snowflake.nextId());

        dailyTrain.setStartDate(dailySeatGenerateRequest.getStartDate());
        dailyTrain.setTrainCode(train.getTrainCode());

        dailyTrain.setStartStation(train.getStartStation());
        long timeStamp = dailySeatGenerateRequest.getStartDate()
                .toEpochSecond(LocalTime.of(0, 0, 0), ZoneOffset.UTC);
        dailyTrain.setStartTime(LocalDateTime.ofEpochSecond(
                timeStamp + train.getStartTime(), 0, ZoneOffset.UTC));
        dailyTrain.setEndStation(train.getEndStation());
        dailyTrain.setEndTime(LocalDateTime.ofEpochSecond(
                timeStamp + train.getEndTime(), 0, ZoneOffset.UTC));

        dailyTrain.setCarriages(train.getCarriages());
        dailyTrainMapper.insert(dailyTrain);

        // 新增每日站点信息
        List<Stop> stops = stopMapper.selectByTrainCodeOrderByIndexDesc(dailySeatGenerateRequest.getTrainCode());
        DailyStop dailyStop = new DailyStop();
        for (Stop stop : stops) {
            dailyStop.setDailyStopId(Snowflake.nextId());
            dailyStop.setDailyTrainId(dailyTrain.getDailyTrainId());

            dailyStop.setStopIndex(stop.getStopIndex());
            dailyStop.setStationName(stop.getStationName());

            dailyStop.setArrivalTime(LocalDateTime.ofEpochSecond(
                    timeStamp + stop.getArrivalTime(), 0, ZoneOffset.UTC));
            dailyStop.setDepartureTime(LocalDateTime.ofEpochSecond(
                    timeStamp + stop.getDepartureTime(), 0, ZoneOffset.UTC));

            dailyStopMapper.insert(dailyStop);
        }

        // 新增每日座位
        String[] carriageMarks = dailyTrain.getCarriages().split("-");
        List<Seat> seats = new ArrayList<>(carriageMarks.length * 200);
        for (int i = 0; i < carriageMarks.length; i++) {
            Carriage carriage = carriageMapper.selectByType(Integer.parseInt(carriageMarks[i]));
            for (int row = 1; row <= carriage.getRowCount(); row++) {
                for (int j = 0; j < carriage.getSeatMark().length(); j++) {
                    Seat seat = new Seat();
                    seat.setDailyTrainId(dailyTrain.getDailyTrainId());
                    seat.setSeatState("0".repeat(stops.size()));
                    seat.setCarriageIndex(i + 1);
                    seat.setRowCount(row);
                    seat.setSeatId(Snowflake.nextId());
                    seat.setColumnMark(carriage.getSeatMark().charAt(j));
                    seats.add(seat);
                }
            }
        }
        seatMapper.insertBatch(seats);
    }

    /**
     * 按日期创建所有列车的每日数据
     */
    @Transactional
    public void generateAll(DailySeatGenerateRequest dailySeatGenerateRequest) {
        List<Train> trains = trainMapper.selectAll();
        for (Train train : trains) {
            dailySeatGenerateRequest.setTrainCode(train.getTrainCode());
            generate(dailySeatGenerateRequest);
        }
    }
}
