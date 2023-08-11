package com.digginroom.digginroom.domain;

import static com.digginroom.digginroom.controller.TestFixture.나무;
import static com.digginroom.digginroom.controller.TestFixture.파워;
import static com.digginroom.digginroom.domain.Genre.AMBIENT;
import static com.digginroom.digginroom.domain.Genre.DANCE;
import static com.digginroom.digginroom.domain.Genre.EXPERIMENTAL;
import static com.digginroom.digginroom.domain.Genre.NEW_AGE;
import static com.digginroom.digginroom.domain.Genre.ROCK;
import static com.digginroom.digginroom.domain.WeightFactor.DISLIKE;
import static com.digginroom.digginroom.domain.WeightStatus.DEFAULT;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.digginroom.digginroom.exception.MemberException.DuplicatedFavoriteException;
import com.digginroom.digginroom.exception.MemberException.EmptyFavoriteException;
import com.digginroom.digginroom.exception.MemberException.FavoriteExistsException;
import com.digginroom.digginroom.exception.RoomException.NotDislikedException;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberTest {

    @Test
    void 멤버는_싫어요한_룸도_여러번_싫어요할_수_있다() {
        Member 파워 = 파워();
        Room 나무 = 나무();

        파워.dislike(나무);
        파워.dislike(나무);
        int weight = getWeight(파워, ROCK);

        assertAll(() -> assertThat(weight).isEqualTo(DEFAULT.getValue() + DISLIKE.getValue() * 2),
                () -> assertThat(파워.getDislikeRooms()).usingRecursiveComparison().isEqualTo(of(나무, 나무)));
    }

    @Test
    void 멤버는_싫어요_취소한_만큼_가중치를_복구한다() {
        Member 파워 = 파워();
        Room 나무 = 나무();

        파워.dislike(나무);
        파워.dislike(나무);
        파워.undislike(나무);
        int weight = getWeight(파워, ROCK);

        assertAll(() -> assertThat(weight).isEqualTo(DEFAULT.getValue() + DISLIKE.getValue()),
                () -> assertThat(파워.getDislikeRooms()).usingRecursiveComparison().isEqualTo(of(나무)));
    }

    @Test
    void 멤버는_싫어요하지_않은_룸을_싫어요_취소할_수_없다() {
        Member 파워 = 파워();

        assertThatThrownBy(() -> 파워.undislike(나무())).isInstanceOf(NotDislikedException.class)
                .hasMessage("싫어요하지 않은 룸입니다.");
    }

    @Test
    void 멤버는_싫어요한_룸도_스크랩_할_수_있다() {
        Member 파워 = 파워();
        Room 나무 = 나무();

        파워.dislike(나무);

        assertDoesNotThrow(() -> 파워.scrap(나무));
    }

    @Test
    void 멤버는_스크랩한_룸도_싫어요_할_수_있다() {
        Member 파워 = 파워();
        Room 나무 = 나무();

        파워.scrap(나무);

        assertDoesNotThrow(() -> 파워.dislike(나무));
    }

    @Test
    void 멤버는_취향을_입력할_수_있다() {
        Member member = 파워();

        member.favorite(of(AMBIENT, ROCK));

        assertThat(member.hasFavorite()).isTrue();
    }

    @Test
    void 멤버에_중복된_취향을_입력한_경우_예외가_발생한다() {
        Member member = 파워();

        assertThatThrownBy(() -> member.favorite(List.of(AMBIENT, ROCK, ROCK)))
                .isInstanceOf(DuplicatedFavoriteException.class);
    }

    @Test
    void 이미_취향이_있는_멤버에_다시_취향을_입력하면_예외가_발생한다() {
        Member member = 파워();
        member.favorite(List.of(AMBIENT, ROCK));

        assertThatThrownBy(() -> member.favorite(List.of(DANCE, NEW_AGE, EXPERIMENTAL)))
                .isInstanceOf(FavoriteExistsException.class);
    }

    @Test
    void 멤버는_취향을_한_개_이상_입력하지_않을_경우_예외가_발생한다() {
        Member member = 파워();

        assertThatThrownBy(() -> member.favorite(List.of()))
                .isInstanceOf(EmptyFavoriteException.class);
    }

    private int getWeight(final Member member, final Genre targetGenre) {
        return member.getMemberGenres()
                .stream()
                .filter(it -> it.isSameGenre(targetGenre))
                .findFirst().get()
                .getWeight();
    }
}
