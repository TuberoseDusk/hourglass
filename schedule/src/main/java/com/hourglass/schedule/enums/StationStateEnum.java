package com.hourglass.schedule.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StationStateEnum {

    ACTIVE(0, "可用"),
    DEACTIVE(0, "停用");

    private final Integer code;
    private final String msg;
}
