package com.hourglass.schedule.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StationCreateRequest {
    @NotBlank(message = "车站名称为空")
    private String stationName;

    private Integer stationState;
}
