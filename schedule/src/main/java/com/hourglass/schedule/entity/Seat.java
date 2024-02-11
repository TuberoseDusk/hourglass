package com.hourglass.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Seat {
    private Long seatId;

    private Long dailyTrainId;
    private Integer carriageIndex;
    private Integer rowCount;
    private Character columnMark;

    private String seatState;
}
