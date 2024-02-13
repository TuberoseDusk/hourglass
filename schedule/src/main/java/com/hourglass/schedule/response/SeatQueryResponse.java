package com.hourglass.schedule.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeatQueryResponse {
    private Long seatId;

    private Long dailyTrainId;
    private Integer carriageIndex;
    private Integer rowCount;
    private Character columnMark;

    private String seatState;

    private Integer seatNumber;
    private Integer seatType;
}