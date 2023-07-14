package com.digginroom.digginroom.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.digginroom.digginroom.controller.dto.ErrorResponse;
import com.digginroom.digginroom.exception.ErrorCode;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorResponse noHandlerFoundException() {
        return new ErrorResponse(LocalDateTime.now(), ErrorCode.NONE, "리소스를 찾을 수 없습니다.");
    }
}
