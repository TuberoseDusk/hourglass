package com.hourglass.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RedisPrefixEnum {

    TICKET_STATE("TICKET:"),
    SEAT_MASK("MASK:");

    private final String prefix;
}
