package com.hourglass.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MQEnum {

    ORDER_CONFIRM("ORDER_CONFIRM");

    private final String topic;
}
