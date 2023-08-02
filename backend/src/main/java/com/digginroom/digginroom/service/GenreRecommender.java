package com.digginroom.digginroom.service;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.MemberGenre;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GenreRecommender {

    private static final Random RANDOM = ThreadLocalRandom.current();

    public Genre recommend(final List<MemberGenre> memberGenres) {
        int weightSum = memberGenres.stream()
                .mapToInt(MemberGenre::getWeight)
                .sum();
        if (weightSum <= 0) {
            throw new IllegalArgumentException("가중치 합이 0입니다");
        }
        int randomizedFactor = RANDOM.nextInt(weightSum);
        return pickByWeight(memberGenres, randomizedFactor);
    }

    private Genre pickByWeight(final List<MemberGenre> memberGenres, final int randomizedFactor) {
        int cursor = 0;
        for (MemberGenre weightedGenre : memberGenres) {
            cursor += weightedGenre.getWeight();
            if (randomizedFactor < cursor) {
                return weightedGenre.getGenre();
            }
        }
        return memberGenres.get(memberGenres.size() - 1).getGenre();
    }
}
