package com.hourglass.schedule.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyTrainQueryResponse {
    private Long dailyTrainId;

    private LocalDate startDate;
    private String trainCode;

    private String startStation;
    private LocalDateTime startTime;

    private String endStation;
    private LocalDateTime endTime;

    private String carriages;
}
