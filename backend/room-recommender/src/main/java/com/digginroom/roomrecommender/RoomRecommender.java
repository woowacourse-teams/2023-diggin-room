package com.digginroom.roomrecommender;

import java.util.List;
import java.util.Random;

public class RoomRecommender {

    private GenreRequest genreRequest;
    private TrackRequest trackRequest;


    public RoomRecommender(GenreRequest genreRequest, TrackRequest trackRequest) {
        this.genreRequest = genreRequest;
        this.trackRequest = trackRequest;
    }

    public Track recommend() {
        MemberGenre recommendedGenre = genreRecommend();
        Track recommendedTrack = trackRecommend(recommendedGenre);
        return recommendedTrack;
    }

    private MemberGenre genreRecommend() {
        List<MemberGenre> superGenres = genreRequest.getSuperGenres();
        int weightSum = superGenres.stream()
                .mapToInt(MemberGenre::getWeight)
                .sum();
        int randomInt = new Random().nextInt(weightSum);

        for (MemberGenre superGenre : superGenres) {
            randomInt -= superGenre.getWeight();
            if (randomInt < 0) {
                return superGenre;
            }
        }
        return superGenres.get(superGenres.size() - 1);
    }

    private Track trackRecommend(MemberGenre genre) {
        List<Track> tracks = trackRequest.getTracksByGenre(genre);
        int randomInt = new Random().nextInt(tracks.size());
        return tracks.get(randomInt);
    }
}
