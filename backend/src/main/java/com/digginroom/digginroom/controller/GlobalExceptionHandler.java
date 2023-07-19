package com.digginroom.digginroom.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.digginroom.digginroom.controller.dto.ErrorResponse;
import com.digginroom.digginroom.exception.DigginRoomException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> noHandlerFoundException() {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(new ErrorResponse(LocalDateTime.now(), "리소스를 찾을 수 없습니다."));
    }

    @ExceptionHandler(DigginRoomException.class)
    public ResponseEntity<ErrorResponse> digginRoomException(final DigginRoomException e) {
        log.info(e.getMessage());

        return ResponseEntity
                .status(e.getHttpStatus())
                .body(new ErrorResponse(LocalDateTime.now(), e.getMessage()));
    }
}
