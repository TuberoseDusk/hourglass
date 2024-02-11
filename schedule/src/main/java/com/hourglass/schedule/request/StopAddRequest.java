package com.hourglass.schedule.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StopAddRequest {
    @NotBlank(message = "列车号为空")
    private String trainCode;
    @NotNull(message = "停站序号为空")
    private Integer stopIndex;

    @NotBlank(message = "停站名称为空")
    private String stationName;
    @NotNull(message = "到达时间为空")
    private Integer arrivalTime;
    @NotNull(message = "离开时间为空")
    private Integer departureTime;
}
