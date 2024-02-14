package com.hourglass.ticket.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeatStateUpdateRequest {
    private Long seatId;
    private Integer startStopIndex;
    private Integer endStopIndex;
    private String flag;
}
