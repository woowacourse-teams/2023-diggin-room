package com.digginroom.digginroom.membergenre.domain;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.recommend.GenreRecommender;
import com.digginroom.digginroom.exception.RecommendException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class DefaultGenreRecommender implements GenreRecommender {

    private final MemberGenreRepository memberGenreRepository;

    @Override
    public Genre recommend(Long memberId) {
        List<MemberGenre> memberGenres = memberGenreRepository.findByMemberId(memberId);
        int weightSum = memberGenres.stream().mapToInt(MemberGenre::getWeight).sum();
        if (weightSum <= 0) {
            throw new RecommendException.UnderBoundWeightException(weightSum);
        }
        return pickByWeight(memberGenres, weightSum);
    }

    private Genre pickByWeight(final List<MemberGenre> memberGenres, final int weightSum) {
        int randomizedFactor = ThreadLocalRandom.current().nextInt(weightSum);
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
