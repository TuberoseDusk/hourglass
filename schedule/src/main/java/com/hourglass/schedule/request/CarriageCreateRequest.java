package com.hourglass.schedule.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarriageCreateRequest {
    private Integer carriageType;
    private Integer rowCount;
    private Integer columnCount;
    private String seatMark;
}
