package com.digginroom.digginroom.domain;

import static com.digginroom.digginroom.TestFixture.파워;
import static org.assertj.core.api.Assertions.assertThat;

import com.digginroom.digginroom.domain.member.MemberGenre;
import com.digginroom.digginroom.domain.member.WeightFactor;
import com.digginroom.digginroom.domain.member.WeightStatus;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberGenreTest {

    @Test
    void 가중치는_100_이하로_떨어질_수_없다() {
        MemberGenre memberGenre = new MemberGenre(Genre.AMBIENT, 파워());

        for (int currentTime = 0; currentTime < 100; currentTime++) {
            memberGenre.adjustWeight(WeightFactor.DISLIKE);
        }

        assertThat(memberGenre.getWeight()).isEqualTo(100);
    }

    @Test
    void 취향에_맞는_장르의_가중치를_증가시킬_수_있다() {
        MemberGenre memberGenre = new MemberGenre(Genre.AMBIENT, 파워());

        memberGenre.adjustWeight(WeightFactor.FAVORITE);

        assertThat(memberGenre.getWeight())
                .isEqualTo(WeightStatus.DEFAULT.getValue() + WeightFactor.FAVORITE.getValue());
    }
}
