package com.digginroom.digginroom.controller.dto;

import com.digginroom.digginroom.domain.Track;

public record TrackResponse(String title, String artist, String superGenre) {

    public static TrackResponse of(Track track) {
        return new TrackResponse(
                track.getTitle(),
                track.getArtist(),
                track.getSuperGenre().getName()
        );
    }
}
