package com.digginroom.digginroom.membergenre.domain;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.recommend.GenreRecommender;
import com.digginroom.digginroom.membergenre.domain.vo.Weight;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenreByWeight implements GenreRecommender {

    private final MemberGenreRepository memberGenreRepository;

    @Override
    public Genre recommend(Long memberId) {
        MemberGenres memberGenres = memberGenreRepository.getAllByMemberId(memberId);
        return pickByWeight(memberGenres);
    }

    private Genre pickByWeight(final MemberGenres memberGenres) {
        int randomizedFactor = ThreadLocalRandom.current().nextInt(memberGenres.sumWeights().getValue());
        Iterator<MemberGenre> iterator = memberGenres.getMemberGenres().iterator();
        MemberGenre current = iterator.next();
        Weight cursor = current.getWeight();

        while (randomizedFactor >= cursor.getValue() && iterator.hasNext()) {
            cursor = cursor.add(current.getWeight());
            current = iterator.next();
        }
        return current.getGenre();
    }
}
