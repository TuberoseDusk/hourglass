package com.hourglass.ticket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TicketStateEnum {
    TO_PAID(1, "待支付"),
    UN_PAID(2, "未支付"),
    TO_USED(3, "待使用"),
    UN_USED(4, "未使用"),
    USED(5, "已使用"),
    REFUNDING(6, "退款中"),
    REFUNDED(7, "已退款");


    private final Integer state;
    private final String msg;
}
