package com.digginroom.digginroom.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.digginroom.digginroom.controller.dto.ErrorResponse;
import com.digginroom.digginroom.exception.DigginRoomException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(final Exception e) {
        log.error(e.getMessage(), e);

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(LocalDateTime.now(), "예상하지 못한 예외가 발생했습니다.(☎ 01049245690)"));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException() {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(new ErrorResponse(LocalDateTime.now(), "리소스를 찾을 수 없습니다."));
    }

    @ExceptionHandler(DigginRoomException.class)
    public ResponseEntity<ErrorResponse> handleDigginRoomException(final DigginRoomException e) {
        if (e.getHttpStatus() == INTERNAL_SERVER_ERROR) {
            log.error(e.getMessage());
        }

        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ErrorResponse(LocalDateTime.now(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        String message = e.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorResponse(LocalDateTime.now(), message));
    }
}
