package com.digginroom.digginroom.membergenre.domain;

import com.digginroom.digginroom.domain.room.Genre;
import com.digginroom.digginroom.exception.MemberException.EmptyFavoriteException;
import com.digginroom.digginroom.exception.MemberException.FavoriteExistsException;
import com.digginroom.digginroom.membergenre.domain.vo.Weight;
import com.digginroom.digginroom.membergenre.domain.vo.WeightFactor;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberGenresTest {

    private MemberGenres memberGenres;

    @BeforeEach
    void setUp() {
        this.memberGenres = new MemberGenres(Arrays.stream(Genre.values())
                .map(genre -> new MemberGenre(genre, 1L))
                .toList()
        );
    }

    @Test
    void 주어진_장르의_가중치를_조절한다() {
        memberGenres.adjustWeight(Genre.ROCK, WeightFactor.SCRAP);

        MemberGenre memberGenre = getMemberGenre(Genre.ROCK);

        Assertions.assertThat(memberGenre.getWeight()).isEqualTo(new Weight(3000));
    }

    private MemberGenre getMemberGenre(final Genre genre) {
        return memberGenres.getMemberGenres()
                .stream()
                .filter(it -> it.isSameGenre(genre))
                .findFirst()
                .get();
    }

    @Test
    void 특정장르들의_가중치를_증가한다() {
        memberGenres.markFavorites(List.of(Genre.DANCE, Genre.ROCK));

        MemberGenre danceGenre = getMemberGenre(Genre.DANCE);
        MemberGenre rockGenre = getMemberGenre(Genre.ROCK);

        SoftAssertions.assertSoftly(softly -> {
            Assertions.assertThat(danceGenre.getWeight()).isEqualTo(new Weight(11000));
            Assertions.assertThat(rockGenre.getWeight()).isEqualTo(new Weight(11000));
        });
    }

    @Test
    void 가중치를_증가할_장르들에_중복이_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> memberGenres.markFavorites(List.of(Genre.DANCE, Genre.DANCE)))
                .isInstanceOf(FavoriteExistsException.class);
    }

    @Test
    void 가중치를_증가할_장르들이_비었다면_에외가_발생한다() {
        assertThatThrownBy(() -> memberGenres.markFavorites(Collections.emptyList()))
                .isInstanceOf(EmptyFavoriteException.class);
    }
}
