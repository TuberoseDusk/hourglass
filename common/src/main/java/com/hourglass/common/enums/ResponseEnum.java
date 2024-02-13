package com.hourglass.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

    SUCCESS(0, "成功"),
    REQUEST_PARAM_ERROR(1, "参数错误"),
    UNKNOWN_ERROR(-1, "未知错误"),

    CARRIAGE_TYPE_EXIST(1001, "车厢类型已存在"),
    STATION_NAME_EXIST(1002, "车站名称已存在"),
    STATION_NAME_NOT_EXIST(1003, "车站不存在"),
    TRAIN_CODE_EXIST(1004, "列车号已存在"),
    TRAIN_CODE_NOT_EXIST(1005, "列车号不存在"),
    ILLEGAL_STOP_INDEX(1006, "停靠站序号非法"),
    CARRIAGE_TYPE_NOT_EXIST(1007, "车厢类型不存在"),
    SEAT_INFORMATION_NOT_EXIST(3001, "座位信息不存在");

    private final Integer code;
    private final String msg;
}
