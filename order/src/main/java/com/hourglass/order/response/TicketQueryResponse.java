package com.hourglass.order.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketQueryResponse {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long dailyTrainId;

    private String trainCode;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long startDailyStopId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    private String startStation;
    private Integer startStopIndex;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long endDailyStopId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private String endStation;
    private Integer endStopIndex;

    private Map<String, Integer> availableTicket;
}
