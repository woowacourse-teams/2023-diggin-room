package com.digginroom.digginroom.membergenre.domain;

import com.digginroom.digginroom.domain.room.Genre;
import com.digginroom.digginroom.membergenre.domain.vo.Weight;
import com.digginroom.digginroom.membergenre.domain.vo.WeightFactor;
import com.digginroom.digginroom.membergenre.domain.vo.WeightStatus;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberGenreTest {

    @Test
    void 가중치는_100_이하로_떨어질_수_없다() {
        MemberGenre memberGenre = new MemberGenre(Genre.AMBIENT, 1L);

        for (int currentTime = 0; currentTime < 100; currentTime++) {
            memberGenre.adjustWeight(WeightFactor.DISLIKE);
        }

        assertThat(memberGenre.getWeight()).isEqualTo(new Weight(100));
    }

    @Test
    void 취향에_맞는_장르의_가중치를_증가시킬_수_있다() {
        MemberGenre memberGenre = new MemberGenre(Genre.AMBIENT, 1L);

        memberGenre.adjustWeight(WeightFactor.FAVORITE);

        assertThat(memberGenre.getWeight())
                .isEqualTo(new Weight(WeightStatus.DEFAULT.getValue() + WeightFactor.FAVORITE.getValue()));
    }
}
