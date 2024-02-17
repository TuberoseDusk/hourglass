package com.hourglass.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RedisPrefixEnum {

    TICKET_STATE("TICKET:"),
    SEAT_MASK("MASK:"),
    ORDER_LOCK("ORDER:"),
    SECTION_CACHE("SECTION:");

    private final String prefix;
}
