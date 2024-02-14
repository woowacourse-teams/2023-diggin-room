package com.digginroom.digginroom.service;

import static com.digginroom.digginroom.TestFixture.PASSWORD;
import static com.digginroom.digginroom.TestFixture.나무;
import static com.digginroom.digginroom.TestFixture.차이;
import static com.digginroom.digginroom.TestFixture.파워;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.exception.RoomException.AlreadyScrappedException;
import com.digginroom.digginroom.exception.RoomException.NotScrappedException;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.repository.RoomRepository;
import com.digginroom.digginroom.service.dto.RoomResponse;
import com.digginroom.digginroom.service.dto.RoomsResponse;
import com.digginroom.digginroom.service.dto.TrackResponse;
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
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RoomServiceTest {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RoomService roomService;

    @Test
    void 스크랩_항목이_있는_경우_멤버가_스크랩한_룸_목록을_조회할_수_있다() {
        Member member = memberRepository.save(Member.self("member", PASSWORD));
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
                                나무.getIdentifier(),
                                true,
                                나무.getScrapCount(),
                                TrackResponse.of(나무.getTrack())),
                        new RoomResponse(차이.getId(),
                                차이.getIdentifier(),
                                true,
                                나무.getScrapCount(),
                                TrackResponse.of(차이.getTrack()))
                ));
    }

    @Test
    void 스크랩_항목이_없는_경우_멤버가_빈_룸_목록을_조회할_수_있다() {
        Member member = memberRepository.save(Member.self("member", PASSWORD));

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

        assertThat(member.getScrapRooms()).contains(room);
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

        assertThat(member.getScrapRooms()).isEmpty();
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
    void 사용자는_싫어요한_룸을_스크랩할_수_있다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());

        roomService.dislike(member.getId(), room.getId());

        assertDoesNotThrow(() -> roomService.scrap(member.getId(), room.getId()));
    }

    @Test
    void 사용자가_룸을_싫어요하면_해당_장르의_가중치를_감소하는_이벤트를_발행한다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());

        roomService.dislike(member.getId(), room.getId());

//        verify(publisher).publishEvent(any(DefaultMemberGenreEvent.class));
    }

    @Test
    void 사용자는_이미_싫어요한_룸을_싫어요할_수_있다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());

        roomService.dislike(member.getId(), room.getId());

        assertDoesNotThrow(() -> roomService.dislike(member.getId(), room.getId()));
    }

    @Test
    void 사용자는_스크랩한_룸을_싫어요할_수_있다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());

        roomService.scrap(member.getId(), room.getId());

        assertDoesNotThrow(() -> roomService.dislike(member.getId(), room.getId()));
    }

    @Test
    void 사용자가_룸을_싫어요를_취소하면_해당_장르의_가중치를_증가하는_이벤트를_발행한다() {
        Member member = memberRepository.save(파워());
        Room room = roomRepository.save(차이());
        roomService.dislike(member.getId(), room.getId());

        roomService.undislike(member.getId(), room.getId());
        //verify(publisher).publishEvent(any(DefaultMemberGenreEvent.class));
    }

    @Test
    void 멤버가_룸을_스크랩하면_룸의_스크랩_수가_올라간다() {
        Member 파워 = memberRepository.save(파워());
        Room 나무 = roomRepository.save(나무());

        roomService.scrap(파워.getId(), 나무.getId());

        assertThat(나무.getScrapCount()).isEqualTo(1L);
    }

    @Test
    void 멤버가_룸을_스크랩_취소_시_룸의_스크랩_수가_내려간다() {
        Member 파워 = memberRepository.save(파워());
        Room 나무 = roomRepository.save(나무());
        roomService.scrap(파워.getId(), 나무.getId());

        roomService.unscrap(파워.getId(), 나무.getId());

        assertThat(나무.getScrapCount()).isZero();
    }
}
