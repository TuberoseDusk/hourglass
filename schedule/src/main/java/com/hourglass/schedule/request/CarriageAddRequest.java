package com.hourglass.schedule.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarriageAddRequest {
    @NotBlank(message = "列车编号为空")
    private String trainCode;

//    暂时只支持在尾部加挂车厢
//    @NotNull(message = "车厢序号为空")
//    private Integer carriageIndex;

    @NotNull(message = "车厢类型为空")
    private Integer carriageType;
}
