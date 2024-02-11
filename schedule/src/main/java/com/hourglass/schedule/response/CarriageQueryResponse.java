package com.hourglass.schedule.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarriageQueryResponse {
    private Integer carriageType;
    private Integer rowCount;
    private Integer columnCount;
    private String seatMark;
}
