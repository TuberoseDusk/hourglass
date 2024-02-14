package com.hourglass.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RedisPrefixEnum {

    TICKET_STATE("TICKET:"),
    SEAT_MASK("MASK:"),
    ORDER_LOCK("ORDER:");

    private final String prefix;
}
