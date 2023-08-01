package com.digginroom.roomrecommender;

import java.util.List;
import java.util.Random;

public class RoomRecommender {

    public static MemberGenre genreRecommend(List<MemberGenre> superGenres) {
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

    public static Track trackRecommend(List<Track> tracks) {
        int randomInt = new Random().nextInt(tracks.size());
        return tracks.get(randomInt);
    }

    public static MemberGenre increaseWeight(MemberGenre targetGenre) {
        targetGenre.increaseWeight();
        return targetGenre;
    }

    public static MemberGenre decreaseWeight(MemberGenre targetGenre) {
        targetGenre.decreaseWeight();
        return targetGenre;
    }
}
