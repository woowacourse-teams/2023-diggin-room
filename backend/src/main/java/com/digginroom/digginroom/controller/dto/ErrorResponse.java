package com.digginroom.digginroom.controller.dto;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timeStamp, String errorMessage) {
}
