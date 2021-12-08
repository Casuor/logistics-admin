package com.aki.springbootlogisticsadmin.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Results handler(RuntimeException e) {
        log.error("+【运行时异常】++++{}", e.getMessage());
        return Results.failRes(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Results handler(IllegalArgumentException e) {
        log.error("+【出现非法数据】++++{}", e.getMessage());
        return Results.failRes(e.getMessage());
    }
}
