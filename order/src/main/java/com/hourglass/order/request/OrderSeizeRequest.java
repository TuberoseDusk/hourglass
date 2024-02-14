package com.hourglass.order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderSeizeRequest {
    private Long dailyTrainId;

    private Integer startStopIndex;
    private Integer endStopIndex;

    private String seatTypeMark;

    private Long userId;
}
