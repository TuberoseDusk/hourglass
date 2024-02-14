package com.hourglass.ticket.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyStopResponse {
    private Long dailyStopId;

    private Long dailyTrainId;
    private Integer stopIndex;

    private String stationName;

    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
}