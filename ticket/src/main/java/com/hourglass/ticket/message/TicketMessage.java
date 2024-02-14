package com.hourglass.ticket.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketMessage {
    private Long userId;

    private Long dailyTrainId;
    private Integer seatNumber;

    private Integer startStopIndex;
    private Integer endStopIndex;

    private Long orderId;
}
