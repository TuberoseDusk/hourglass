package com.hourglass.common.response;

import com.hourglass.common.enums.ResponseEnum;
import lombok.Getter;

@Getter
public class Response<T> {
    private final Integer code;
    private final String msg;
    private final T data;

    private Response() {
        this(ResponseEnum.SUCCESS);
    }

    private Response(ResponseEnum responseEnum) {
        code = responseEnum.getCode();
        msg = responseEnum.getMsg();
        data = null;
    }

    private Response(T data) {
        code = ResponseEnum.SUCCESS.getCode();
        msg = ResponseEnum.SUCCESS.getMsg();
        this.data = data;
    }

    private Response(ResponseEnum responseEnum, T data) {
        code = responseEnum.getCode();
        msg = responseEnum.getMsg();
        this.data = data;
    }

    public static Response<Void> success() {
        return new Response<>();
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(data);
    }

    public static Response<Void> error() {
        return new Response<>(ResponseEnum.UNKNOWN_ERROR);
    }

    public static Response<Void> error(ResponseEnum responseEnum) {
        return new Response<>(responseEnum);
    }

    public static <T> Response<T> error(ResponseEnum responseEnum, T data) {
        return new Response<>(responseEnum, data);
    }
}
