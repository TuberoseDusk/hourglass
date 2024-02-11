package com.hourglass.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Stop {
    private String trainCode;
    private Integer stopIndex;

    private String stationName;
    private Integer arrivalTime;
    private Integer departureTime;
}
