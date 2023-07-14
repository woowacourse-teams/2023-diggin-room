package com.digginroom.digginroom.controller.dto;

import com.digginroom.digginroom.exception.ErrorCode;
import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timeStamp, ErrorCode errorCode, String errorMessage) {
}
