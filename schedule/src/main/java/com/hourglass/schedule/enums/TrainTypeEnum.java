package com.hourglass.schedule.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TrainTypeEnum {

    G(1, "高铁"),
    D(2, "动车"),
    T(3, "特快"),
    K(4, "快车");

    private final Integer type;
    private final String msg;
}
