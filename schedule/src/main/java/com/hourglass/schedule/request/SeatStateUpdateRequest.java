package com.hourglass.schedule.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeatStateUpdateRequest {
    @NotNull(message = "座位ID为空")
    private Long seatId;
    @NotNull(message = "出发站为空")
    private Integer startStopIndex;
    @NotNull(message = "到达站为空")
    private Integer endStopIndex;
    @NotBlank(message = "填充字段为空")
    private String flag;
}
