package com.hourglass.schedule.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StationQueryResponse {
    private String stationName;
    private Integer stationState;
}
