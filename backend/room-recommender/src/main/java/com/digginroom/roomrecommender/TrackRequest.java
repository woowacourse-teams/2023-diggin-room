package com.digginroom.roomrecommender;

import java.util.List;

public interface TrackRequest {

    List<Track> getTracksByGenre(MemberGenre genre);
}
