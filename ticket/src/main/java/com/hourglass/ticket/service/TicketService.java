package com.hourglass.ticket.service;

import com.hourglass.common.enums.ResponseEnum;
import com.hourglass.common.exception.BusinessException;
import com.hourglass.common.response.Response;
import com.hourglass.common.util.Snowflake;
import com.hourglass.ticket.entity.Ticket;
import com.hourglass.ticket.feign.ScheduleFeignService;
import com.hourglass.ticket.mapper.TicketMapper;
import com.hourglass.ticket.message.TicketMessage;
import com.hourglass.ticket.request.SeatStateUpdateRequest;
import com.hourglass.ticket.response.DailyStopResponse;
import com.hourglass.ticket.response.DailyTrainQueryResponse;
import com.hourglass.ticket.response.SeatQueryResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    @Resource
    private TicketMapper ticketMapper;

    @Resource
    private ScheduleFeignService scheduleFeignService;

    public void create(TicketMessage ticketMessage) {
        Ticket ticket = new Ticket();
        ticket.setTicketId(Snowflake.nextId());
        ticket.setUserId(ticketMessage.getUserId());
        ticket.setDailyTrainId(ticketMessage.getDailyTrainId());
        ticket.setSeatNumber(ticketMessage.getSeatNumber());
        ticket.setStartStopIndex(ticketMessage.getStartStopIndex());
        ticket.setEndStopIndex(ticketMessage.getEndStopIndex());
        ticket.setOrderId(ticketMessage.getOrderId());

        Response<DailyTrainQueryResponse> dailyTrainQueryResponseResponse = scheduleFeignService.queryDailyTrainById(ticket.getDailyTrainId());
        if (!ResponseEnum.SUCCESS.getCode().equals(dailyTrainQueryResponseResponse.getCode())) {
            throw new BusinessException(ResponseEnum.UNKNOWN_ERROR);
        }

        ticket.setTrainCode(dailyTrainQueryResponseResponse.getData().getTrainCode());

        Response<SeatQueryResponse> seatQueryResponseResponse = scheduleFeignService.querySeatSingle(ticket.getDailyTrainId(), ticket.getSeatNumber());
        if (!ResponseEnum.SUCCESS.getCode().equals(seatQueryResponseResponse.getCode())) {
            throw new BusinessException(ResponseEnum.UNKNOWN_ERROR);
        }

        ticket.setRowCount(seatQueryResponseResponse.getData().getRowCount());
        ticket.setColumnMark(seatQueryResponseResponse.getData().getColumnMark());

        Response<DailyStopResponse> dailyStopResponseResponse = scheduleFeignService.queryDailyStop(ticket.getDailyTrainId(), ticket.getStartStopIndex());
        if (!ResponseEnum.SUCCESS.getCode().equals(dailyStopResponseResponse.getCode())) {
            throw new BusinessException(ResponseEnum.UNKNOWN_ERROR);
        }

        ticket.setStartDailyStopId(dailyStopResponseResponse.getData().getDailyStopId());
        ticket.setStartStation(dailyStopResponseResponse.getData().getStationName());
        ticket.setStartTime(dailyStopResponseResponse.getData().getDepartureTime());

        dailyStopResponseResponse = scheduleFeignService.queryDailyStop(ticket.getDailyTrainId(), ticket.getEndStopIndex());
        if (!ResponseEnum.SUCCESS.getCode().equals(dailyStopResponseResponse.getCode())) {
            throw new BusinessException(ResponseEnum.UNKNOWN_ERROR);
        }
        ticket.setEndDailyStopId(dailyStopResponseResponse.getData().getDailyStopId());
        ticket.setEndStation(dailyStopResponseResponse.getData().getStationName());
        ticket.setEndTime(dailyStopResponseResponse.getData().getArrivalTime());

        ticketMapper.insert(ticket);

        SeatStateUpdateRequest seatStateUpdateRequest = new SeatStateUpdateRequest(
                seatQueryResponseResponse.getData().getSeatId(),
                ticket.getStartStopIndex(), ticket.getEndStopIndex(), "1");
        scheduleFeignService.updateState(seatStateUpdateRequest);

    }
}
