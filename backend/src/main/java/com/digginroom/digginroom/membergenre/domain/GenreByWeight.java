package com.digginroom.digginroom.membergenre.domain;

import com.digginroom.digginroom.domain.room.Genre;
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
        int picker = ThreadLocalRandom.current().nextInt(memberGenres.sumWeights().getValue());
        Iterator<MemberGenre> sections = memberGenres.getMemberGenres().iterator();
        MemberGenre section = sections.next();
        Weight cumulated = section.getWeight();

        while (cumulated.isLessThan(picker) && sections.hasNext()) {
            cumulated = cumulated.add(section.getWeight());
            section = sections.next();
        }
        return section.getGenre();
    }
}
