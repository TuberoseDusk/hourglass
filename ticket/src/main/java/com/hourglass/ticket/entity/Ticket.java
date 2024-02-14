package com.hourglass.ticket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {

    private Long ticketId;

    private Long userId;

    private Long dailyTrainId;
    private String trainCode;

    private Integer seatNumber;
    private Integer rowCount;
    private Character columnMark;

    private Long startDailyStopId;
    private Integer startStopIndex;
    private LocalDateTime startTime;
    private String startStation;

    private Long endDailyStopId;
    private Integer endStopIndex;
    private LocalDateTime endTime;
    private String endStation;

    private Long orderId;
}
