package com.digginroom.digginroom.service.dto;

public record RoomResponse(Long roomId, String videoId, boolean isScrapped, Long scrapCount, TrackResponse track) {
}
