package com.hourglass.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SeatTypeEnum {

    G_BUSINESS(100, "高铁商务座"),
    G_FIRST(101, "高铁一等座"),
    G_SECOND(102, "高铁二等座");

    private final Integer type;
    private final String msg;
}
