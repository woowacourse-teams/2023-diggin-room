package com.digginroom.digginroom.controller.dto;

public record RoomResponse(Long roomId, String videoId, boolean isScrapped, TrackResponse track) {
}
