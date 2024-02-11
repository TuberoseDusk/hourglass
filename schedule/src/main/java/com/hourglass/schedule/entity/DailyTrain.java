package com.hourglass.schedule.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DailyTrain {
    private Long dailyTrainId;

    private LocalDate startDate;
    private String trainCode;

    private String startStation;
    private LocalDateTime startTime;

    private String endStation;
    private LocalDateTime endTime;

    private String carriages;
}
