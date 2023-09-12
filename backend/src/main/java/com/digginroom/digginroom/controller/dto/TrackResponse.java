package com.digginroom.digginroom.controller.dto;

import com.digginroom.digginroom.domain.track.Track;

public record TrackResponse(String title, String artist, String superGenre, String description) {

    public static TrackResponse of(Track track) {
        return new TrackResponse(
                track.getTitle(),
                track.getArtist(),
                track.getSuperGenre().getName(),
                track.getDescription()
        );
    }
}
