package com.digginroom.digginroom.playlist.infra.dto;

public record YoutubePlayListItemRequest(Snippet snippet) {

    public record Snippet(String playlistId, ResourceId resourceId) {
        public record ResourceId(String kind, String videoId) {
        }
    }
}
