package com.hourglass.common.exception;

import com.hourglass.common.enums.ResponseEnum;
import com.hourglass.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Response<List<String>> bindExceptionHandler(BindException e) {
        List<String> messages = new ArrayList<>();
        for (ObjectError objectError : e.getBindingResult().getAllErrors()) {
            String msg = objectError.getDefaultMessage();
            messages.add(msg);
            log.info(msg);
        }
        return Response.error(ResponseEnum.REQUEST_PARAM_ERROR, messages);
    }

    @ExceptionHandler(BusinessException.class)
    public Response<Void> businessExceptionHandler(BusinessException e) {
        log.error(e.getMessage());
        return Response.error(e.getResponseEnum());
    }

    @ExceptionHandler(Exception.class)
    public Response<Void> exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return Response.error();
    }
}
