package com.hourglass.common.exception;

import com.hourglass.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final ResponseEnum responseEnum;

    public BusinessException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg(), null, false, false);
        this.responseEnum = responseEnum;
    }
}
