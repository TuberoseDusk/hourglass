package com.hourglass.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Section {
    private Long dailyTrainId;

    private Long startDailyStopId;
    private LocalDateTime startTime;
    private String startStation;
    private Integer startStopIndex;

    private Long endDailyStopId;
    private LocalDateTime endTime;
    private String endStation;
    private Integer endStopIndex;
}
