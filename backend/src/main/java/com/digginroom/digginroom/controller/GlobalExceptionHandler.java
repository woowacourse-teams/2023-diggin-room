package com.digginroom.digginroom.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.digginroom.digginroom.service.dto.ErrorResponse;
import com.digginroom.digginroom.exception.DigginRoomException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, BindException.class})
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

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupported(
            final HttpRequestMethodNotSupportedException e
    ) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ErrorResponse(LocalDateTime.now(), "HTTP 메서드를 확인해주세요."));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException() {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorResponse(LocalDateTime.now(), "요청 페이로드를 확인해주세요."));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
            final MissingServletRequestParameterException e
    ) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ErrorResponse(LocalDateTime.now(), "쿼리 스트링을 확인해주세요."));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(
            final HttpMediaTypeNotSupportedException e
    ) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ErrorResponse(LocalDateTime.now(), "미디어 타입을 확인해주세요."));
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponse> handleMissingPathVariableException(final MissingPathVariableException e) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(new ErrorResponse(LocalDateTime.now(), "경로 매개변수 확인해주세요."));
    }
}
