package com.digginroom.digginroom.service;

import static com.digginroom.digginroom.controller.TestFixture.나무;
import static com.digginroom.digginroom.controller.TestFixture.차이;
import static com.digginroom.digginroom.controller.TestFixture.파워;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.digginroom.digginroom.controller.dto.RoomResponse;
import com.digginroom.digginroom.controller.dto.RoomsResponse;
import com.digginroom.digginroom.controller.dto.TrackResponse;
import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.domain.Room;
import com.digginroom.digginroom.exception.RoomException.AlreadyDislikeException;
import com.digginroom.digginroom.exception.RoomException.AlreadyScrappedException;
import com.digginroom.digginroom.exception.RoomException.NotDislikedException;
import com.digginroom.digginroom.exception.RoomException.NotScrappedException;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.repository.RoomRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class RoomServiceTest {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomService roomService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 페이징을_사용하여_여러_룸_중_랜덤으로_하나를_선택한다() {
        Member member = memberRepository.save(파워());
        roomRepository.save(나무());
        roomRepository.save(차이());

        final var pickedRoom = roomService.recommend(member.getId());

        assertThat(pickedRoom).isNotNull();
    }

    @Test
    void 스크랩_항목이_있는_경우_멤버가_스크랩한_룸_목록을_조회할_수_있다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room 나무 = 나무();
        Room 차이 = 차이();
        roomRepository.save(나무);
        roomRepository.save(차이);
        member.scrap(나무);
        member.scrap(차이);

        RoomsResponse scrappedRooms = roomService.findScrappedRooms(member.getId());

        assertThat(scrappedRooms.rooms())
                .usingRecursiveComparison()
                .isEqualTo(List.of(
                        new RoomResponse(
                                나무.getId(),
                                나무.getMediaSource().getIdentifier(),
                                true,
                                TrackResponse.of(나무.getTrack())),
                        new RoomResponse(차이.getId(),
                                차이.getMediaSource().getIdentifier(),
                                true,
                                TrackResponse.of(차이.getTrack()))
                ));
    }

    @Test
    void 스크랩_항목이_없는_경우_멤버가_빈_룸_목록을_조회할_수_있다() {
        Member member = memberRepository.save(new Member("member", "1234"));

        RoomsResponse scrappedRooms = roomService.findScrappedRooms(member.getId());

        assertThat(scrappedRooms.rooms())
                .usingRecursiveComparison()
                .isEqualTo(Collections.emptyList());
    }

    @Test
    void 멤버는_룸을_스크랩할_수_있다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());

        roomService.scrap(member.getId(), room.getId());

        assertThat(member.getScrapRooms().getScrapRooms()).contains(room);
    }

    @Test
    void 이미_스크랩된_룸은_다시_스크랩되지_않는다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());

        roomService.scrap(member.getId(), room.getId());
        assertThatThrownBy(() -> roomService.scrap(member.getId(), room.getId()))
                .isInstanceOf(AlreadyScrappedException.class)
                .hasMessageContaining("이미 스크랩된 룸입니다.");
    }

    @Test
    void 멤버는_룸스크랩을_취소할_수_있다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());
        roomService.scrap(member.getId(), room.getId());

        roomService.unscrap(member.getId(), room.getId());

        assertThat(member.getScrapRooms().getScrapRooms()).isEmpty();
    }

    @Test
    void 이미_스크랩_취소된_룸은_다시_취소되지_않는다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());
        Room otherRoom = roomRepository.save(나무());

        roomService.scrap(member.getId(), room.getId());
        roomService.scrap(member.getId(), otherRoom.getId());

        roomService.unscrap(member.getId(), room.getId());
        assertThatThrownBy(() -> roomService.unscrap(member.getId(), room.getId()))
                .isInstanceOf(NotScrappedException.class)
                .hasMessageContaining("스크랩되지 않은 룸입니다.");
    }

    @Test
    void 사용자는_싫어요한_룸을_스크랩할_수_없다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());

        roomService.dislike(member.getId(), room.getId());

        assertThatThrownBy(() -> roomService.scrap(member.getId(), room.getId()))
                .isInstanceOf(AlreadyDislikeException.class)
                .hasMessageContaining("이미 싫어요한 룸입니다.");
    }

    @Test
    void 사용자는_룸을_싫어요_할_수_있다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());

        roomService.dislike(member.getId(), room.getId());

        assertThat(member.getDislikeRooms().getDislikeRooms()).isNotEmpty();
    }

    @Test
    void 사용자는_이미_싫어요한_룸을_싫어요할_수_없다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());

        roomService.dislike(member.getId(), room.getId());

        assertThatThrownBy(() -> roomService.dislike(member.getId(), room.getId()))
                .isInstanceOf(AlreadyDislikeException.class)
                .hasMessageContaining("이미 싫어요한 룸입니다.");
    }

    @Test
    void 사용자는_스크랩한_룸을_싫어요할_수_없다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());

        roomService.scrap(member.getId(), room.getId());

        assertThatThrownBy(() -> roomService.dislike(member.getId(), room.getId()))
                .isInstanceOf(AlreadyScrappedException.class)
                .hasMessageContaining("이미 스크랩된 룸입니다.");
    }

    @Test
    void 사용자는_싫어요한_룸을_취소할_수_있다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());
        roomService.dislike(member.getId(), room.getId());

        roomService.undislike(member.getId(), room.getId());

        assertThat(member.getDislikeRooms().getDislikeRooms()).isEmpty();
    }

    @Test
    void 사용자는_싫어요하지_않은_룸을_취소할_수_없다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());

        assertThatThrownBy(() -> roomService.undislike(member.getId(), room.getId()))
                .isInstanceOf(NotDislikedException.class)
                .hasMessageContaining("싫어요하지 않은 룸입니다.");
    }

    @Test
    void 멤버가_룸을_스크랩하면_가중치가_올라간다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());
        Genre targetGenre = room.getTrack().getSuperGenre();
        int originalWeight = getWeight(member, targetGenre);

        roomService.scrap(member.getId(), room.getId());

        int resultWeight = getWeight(member, targetGenre);
        assertThat(resultWeight).isGreaterThan(originalWeight);
    }

    private int getWeight(final Member member, final Genre targetGenre) {
        return member.getMemberGenres().getMemberGenres().stream()
                .filter(it -> it.isSameGenre(targetGenre))
                .findFirst()
                .get().getWeight();
    }

    @Test
    void 멤버가_룸을_스크랩을_취소하면_가중치가_돌아간다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());
        Genre targetGenre = room.getTrack().getSuperGenre();
        int originalWeight = getWeight(member, targetGenre);
        roomService.scrap(member.getId(), room.getId());

        roomService.unscrap(member.getId(), room.getId());

        int resultWeight = getWeight(member, targetGenre);
        assertThat(resultWeight).isEqualTo(originalWeight);
    }

    @Test
    void 멤버가_룸을_싫어요하면_가중치가_내려간다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());
        Genre targetGenre = room.getTrack().getSuperGenre();
        int originalWeight = getWeight(member, targetGenre);

        roomService.dislike(member.getId(), room.getId());

        int resultWeight = getWeight(member, targetGenre);
        assertThat(resultWeight).isLessThan(originalWeight);
    }

    @Test
    void 멤버가_룸을_싫어요를_취소하면_가중치가_돌아간다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());
        Genre targetGenre = room.getTrack().getSuperGenre();
        int originalWeight = getWeight(member, targetGenre);
        roomService.dislike(member.getId(), room.getId());

        roomService.undislike(member.getId(), room.getId());

        int resultWeight = getWeight(member, targetGenre);
        assertThat(resultWeight).isEqualTo(originalWeight);
    }
}
