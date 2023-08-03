package com.digginroom.digginroom.domain;

import static com.digginroom.digginroom.controller.TestFixture.파워;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberGenreTest {

    @Test
    void 가중치는_0_이하로_떨어질_수_없다() {
        MemberGenre memberGenre = new MemberGenre(Genre.AMBIENT, 파워());

        for (int currentTime = 0; currentTime < 100; currentTime++) {
            memberGenre.adjustWeight(Weight.DISLIKE);
        }

        assertThat(memberGenre.getWeight()).isEqualTo(0);
    }
}
