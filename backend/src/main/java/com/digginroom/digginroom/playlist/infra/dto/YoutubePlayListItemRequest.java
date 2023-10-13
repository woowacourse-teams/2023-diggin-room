package com.digginroom.digginroom.playlist.infra.dto;

import com.digginroom.digginroom.playlist.infra.dto.YoutubePlayListItemRequest.Snippet.ResourceId;

public record YoutubePlayListItemRequest(Snippet snippet) {

    public record Snippet(String playlistId, ResourceId resourceId) {
        public record ResourceId(String kind, String videoId) {
        }
    }

    public static YoutubePlayListItemRequest of(final String playListId, final String videoId) {
        return new YoutubePlayListItemRequest(
                new YoutubePlayListItemRequest.Snippet(playListId,
                        new ResourceId("youtube#video", videoId)
                )
        );
    }
}
