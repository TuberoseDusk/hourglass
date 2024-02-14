package com.hourglass.order.service;

import cn.hutool.core.bean.BeanUtil;
import com.hourglass.common.enums.ResponseEnum;
import com.hourglass.common.enums.SeatTypeEnum;
import com.hourglass.common.exception.BusinessException;
import com.hourglass.common.response.Response;
import com.hourglass.order.feign.ScheduleFeignService;
import com.hourglass.order.repository.SeatTypeRepository;
import com.hourglass.order.repository.TicketRepository;
import com.hourglass.order.request.TicketGenerateRequest;
import com.hourglass.order.response.DailyTrainQueryResponse;
import com.hourglass.order.response.SeatQueryResponse;
import com.hourglass.order.response.SectionQueryResponse;
import com.hourglass.order.response.TicketQueryResponse;
import jakarta.annotation.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.*;

@Service
public class TicketService {

    @Resource
    private ScheduleFeignService scheduleFeignService;

    @Resource
    private TicketRepository ticketRepository;

    @Resource
    private SeatTypeRepository seatTypeRepository;

    @Transactional
    public void generateAll(TicketGenerateRequest ticketGenerateRequest) {
        Response<List<DailyTrainQueryResponse>> dailyTrainRes = scheduleFeignService.queryDailyTrain(ticketGenerateRequest.getStartDate());
        // 远程调用失败
        if (!ResponseEnum.SUCCESS.getCode().equals(dailyTrainRes.getCode())) {
            throw new BusinessException(ResponseEnum.UNKNOWN_ERROR);
        }

        // 获取该日期发车的所有车次
        List<DailyTrainQueryResponse> dailyTrains = dailyTrainRes.getData();
        for (DailyTrainQueryResponse dailyTrainQueryResponse : dailyTrains) {
            generate(dailyTrainQueryResponse.getDailyTrainId());
        }

    }

    @Transactional
    public void generate(Long dailyTrainId) {
        Response<List<SeatQueryResponse>> seatRes = scheduleFeignService.querySeat(dailyTrainId);
        if (!ResponseEnum.SUCCESS.getCode().equals(seatRes.getCode())) {
            throw new BusinessException(ResponseEnum.UNKNOWN_ERROR);
        }

        List<SeatQueryResponse> seats = seatRes.getData();
        if (seats == null || seats.isEmpty()) {
            return;
        }

        // 停站总数
        int stopNum = seats.get(0).getSeatState().length();
        // 最大座位号
        int maxSeatNumber = seats.get(seats.size() - 1).getSeatNumber();
        // 有效座位类型
        Set<Integer> validSeatTypes = new HashSet<>();

        for (SeatQueryResponse seat : seats) {
            validSeatTypes.add(seat.getSeatType());
            maxSeatNumber = Math.max(maxSeatNumber, seat.getSeatNumber());
        }

        // 创建座位信息BitSet，空闲设1， 其它设0
        for (int i = 0; i < stopNum; i++) {
            BitSet ticketState = new BitSet(maxSeatNumber + 1);
            for (SeatQueryResponse seat : seats) {
                if (seat.getSeatState().charAt(i) == '0') {
                    ticketState.set(seat.getSeatNumber());
                } else {
                    ticketState.clear(seat.getSeatNumber());
                }
            }
            ticketRepository.insertTicketOfDailyTrainAtStation(dailyTrainId, i, ticketState);
        }

        // 创建座位类型mask，对应座位类型设1， 其它设0
        for (Integer seatType : validSeatTypes) {
            BitSet seatTypeMask = new BitSet(maxSeatNumber + 1);
            for (SeatQueryResponse seat : seats) {
                if (seatType.equals(seat.getSeatType())) {
                    seatTypeMask.set(seat.getSeatNumber());
                } else {
                    seatTypeMask.clear(seat.getSeatNumber());
                }
            }
            seatTypeRepository.insertSeatTypeMask(dailyTrainId, seatType, seatTypeMask);
        }
    }

    /**
     *查询乘车线路的剩余票数
     */
    public Map<String, Integer> queryTicket(Long dailyTrainId, Integer startStopIndex, Integer endStopIndex) {

        // 获取座位状态
        BitSet ticket = selectTicketState(dailyTrainId, startStopIndex, endStopIndex);

        // 保存座位类型
        Map<String, Integer> availableSeats = new HashMap<>();
        for (SeatTypeEnum seatTypeEnum : SeatTypeEnum.values()) {
            // 遍历查询所有座位类型
            BitSet seatMask = seatTypeRepository.selectSeatTypeMask(dailyTrainId, seatTypeEnum.getType());
            if (seatMask == null) {
                // 车次不存在该类型座位
                continue;
            }
            // 与运算获得对应类型的空闲座位
            seatMask.and(ticket);

            availableSeats.put(seatTypeEnum.getMsg(), seatMask.cardinality());
        }
        return availableSeats;
    }

    /**
     * 扣库存
     */
    public Integer decreaseTicket(Long dailyTrainId, Integer startStopIndex, Integer endStopIndex, String seatTypeMark) {
        // 获取座位状态
        BitSet ticket = selectTicketState(dailyTrainId, startStopIndex, endStopIndex);
        for (SeatTypeEnum seatTypeEnum : SeatTypeEnum.values()) {
            if (seatTypeEnum.getMsg().equals(seatTypeMark)) {
                // 找到对应座位类型
                BitSet seatTypeMask = seatTypeRepository.selectSeatTypeMask(dailyTrainId, seatTypeEnum.getType());
                ticket.and(seatTypeMask);   // 与运算得到对应座位类型余票

                // 第一个可用位置
                int seatNumber = ticket.nextSetBit(0);

                // 修改 [startStop, EndStop) 之间的座位状态
                for (int stopIndex = startStopIndex; stopIndex < endStopIndex; stopIndex++) {
                    ticketRepository.occupyTicketOfDailyTrainAtStation(dailyTrainId, stopIndex, seatNumber);
                }
                return seatNumber;
            }
        }
        return null;
    }

    private BitSet selectTicketState(Long dailyTrainId, Integer startStopIndex, Integer endStopIndex) {
        // 获取途径站点的座位状态
        List<BitSet> ticketStates = new ArrayList<>(endStopIndex - startStopIndex);
        for (Integer stopIndex = startStopIndex; stopIndex < endStopIndex; stopIndex++) {
            BitSet ticketState = ticketRepository.selectTicketOfDailyTrainAtStation(dailyTrainId, stopIndex);
            if (ticketState == null) {
                // 对应车站的座位信息不存在
                throw new BusinessException(ResponseEnum.SEAT_INFORMATION_NOT_EXIST);
            }
            ticketStates.add(ticketState);
        }

        // 执行与运算，得到空闲座位
        BitSet ticket = ticketStates.remove(ticketStates.size() - 1);
        for (BitSet ticketState : ticketStates) {
            ticket.and(ticketState);
        }
        return ticket;
    }

    public List<TicketQueryResponse> query(String startStation, String endStation, LocalDate date) {
        Response<List<SectionQueryResponse>> sectionRes = scheduleFeignService.querySection(startStation, endStation, date);
        if (!ResponseEnum.SUCCESS.getCode().equals(sectionRes.getCode())) {
            throw new BusinessException(ResponseEnum.UNKNOWN_ERROR);
        }

        List<TicketQueryResponse> ticketQueryResponses = new ArrayList<>(sectionRes.getData().size());
        for (SectionQueryResponse sectionQueryResponse : sectionRes.getData()) {
            Map<String, Integer> availableTickets= queryTicket(
                    sectionQueryResponse.getDailyTrainId(),
                    sectionQueryResponse.getStartStopIndex(),
                    sectionQueryResponse.getEndStopIndex());
            TicketQueryResponse ticketQueryResponse = BeanUtil.copyProperties(sectionQueryResponse, TicketQueryResponse.class);
            ticketQueryResponse.setAvailableTicket(availableTickets);
            ticketQueryResponses.add(ticketQueryResponse);
        }
        return ticketQueryResponses;
    }

}
