package com.hourglass.schedule.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainCreateRequest {
    @NotBlank(message = "列车代码为空")
    private String trainCode;
    @NotNull(message = "列车类型为空")
    private Integer trainType;

    @NotBlank(message = "始发站为空")
    private String startStation;
    @NotNull(message = "出发时间为空")
    private Integer startTime;

    @NotBlank(message = "终点站为空")
    private String endStation;
    @NotNull(message = "到达时间为空")
    private Integer endTime;
}
