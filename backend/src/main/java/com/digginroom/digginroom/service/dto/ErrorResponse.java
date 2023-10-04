package com.digginroom.digginroom.service.dto;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timeStamp, String errorMessage) {
}
