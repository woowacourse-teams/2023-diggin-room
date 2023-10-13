package com.digginroom.digginroom.playlist.infra.dto;

public record YouTubePlayListRequest(Snippet snippet) {
    public record Snippet(String title) {
    }
}
