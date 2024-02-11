package com.hourglass.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Train {
    private String trainCode;
    private Integer trainType;

    private String startStation;
    private Integer startTime;

    private String endStation;
    private Integer endTime;

    private String carriages;
}
