package com.digginroom.digginroom.domain;

import static com.digginroom.digginroom.controller.TestFixture.나무;
import static com.digginroom.digginroom.controller.TestFixture.파워;
import static com.digginroom.digginroom.domain.Weight.DEFAULT;
import static com.digginroom.digginroom.domain.Weight.DISLIKE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
        int weight = getWeight(파워, Genre.ROCK);

        assertAll(() -> assertThat(weight).isEqualTo(DEFAULT.getWeight() + DISLIKE.getWeight() * 2),
                () -> assertThat(파워.getDislikeRooms()).usingRecursiveComparison().isEqualTo(List.of(나무, 나무)));
    }

    @Test
    void 멤버는_싫어요_취소한_만큼_가중치를_복구한다() {
        Member 파워 = 파워();
        Room 나무 = 나무();

        파워.dislike(나무);
        파워.dislike(나무);
        파워.undislike(나무);
        int weight = getWeight(파워, Genre.ROCK);

        assertAll(() -> assertThat(weight).isEqualTo(DEFAULT.getWeight() + DISLIKE.getWeight()),
                () -> assertThat(파워.getDislikeRooms()).usingRecursiveComparison().isEqualTo(List.of(나무)));
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

    private int getWeight(final Member member, final Genre targetGenre) {
        return member.getMemberGenres()
                .stream()
                .filter(it -> it.isSameGenre(targetGenre))
                .findFirst().get()
                .getWeight();
    }
}
