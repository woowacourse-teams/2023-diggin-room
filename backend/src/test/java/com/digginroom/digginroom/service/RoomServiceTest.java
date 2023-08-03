package com.digginroom.digginroom.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.digginroom.digginroom.domain.MediaSource;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.domain.Room;
import com.digginroom.digginroom.exception.RoomException.AlreadyDislikeException;
import com.digginroom.digginroom.exception.RoomException.AlreadyScrappedException;
import com.digginroom.digginroom.exception.RoomException.NotDislikedException;
import com.digginroom.digginroom.exception.RoomException.NotScrappedException;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.repository.RoomRepository;
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
        Member member = memberRepository.save(new Member("member", "1234"));
        Room 나무 = new Room(new MediaSource("lQcnNPqy2Ww"));
        Room 가까운듯먼그대여 = new Room(new MediaSource("2VkWaOOF4Rc"));
        roomRepository.save(나무);
        roomRepository.save(가까운듯먼그대여);

        final var pickedRoom = roomService.recommend(member.getId());

        assertThat(pickedRoom).isNotNull();
    }

    @Test
    void 멤버는_룸을_스크랩할_수_있다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room room = roomRepository.save(new Room(new MediaSource("lQcnNPqy2Ww")));

        roomService.scrap(member.getId(), room.getId());

        assertThat(member.getScraps().getScrapRooms()).contains(room);
    }

    @Test
    void 이미_스크랩된_룸은_다시_스크랩되지_않는다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room room = roomRepository.save(new Room(new MediaSource("lQcnNPqy2Ww")));

        roomService.scrap(member.getId(), room.getId());
        assertThatThrownBy(() -> roomService.scrap(member.getId(), room.getId()))
                .isInstanceOf(AlreadyScrappedException.class)
                .hasMessageContaining("이미 스크랩된 룸입니다.");
    }

    @Test
    void 멤버는_룸스크랩을_취소할_수_있다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room room = roomRepository.save(new Room(new MediaSource("lQcnNPqy2Ww")));
        roomService.scrap(member.getId(), room.getId());

        roomService.unscrap(member.getId(), room.getId());

        assertThat(member.getScraps().getScrapRooms()).isEmpty();
    }

    @Test
    void 이미_스크랩_취소된_룸은_다시_취소되지_않는다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room room = roomRepository.save(new Room(new MediaSource("lQcnNPqy2Ww")));
        Room otherRoom = roomRepository.save(new Room(new MediaSource("lQcnNPqy3Ww")));

        roomService.scrap(member.getId(), room.getId());
        roomService.scrap(member.getId(), otherRoom.getId());

        roomService.unscrap(member.getId(), room.getId());
        assertThatThrownBy(() -> roomService.unscrap(member.getId(), room.getId()))
                .isInstanceOf(NotScrappedException.class)
                .hasMessageContaining("스크랩되지 않은 룸입니다.");
    }

    @Test
    void 사용자는_싫어요한_룸을_스크랩할_수_없다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room room = roomRepository.save(new Room(new MediaSource("lQcnNPqy2Ww")));

        roomService.dislike(member.getId(), room.getId());

        assertThatThrownBy(() -> roomService.scrap(member.getId(), room.getId()))
                .isInstanceOf(AlreadyDislikeException.class)
                .hasMessageContaining("이미 싫어요한 룸입니다.");
    }

    @Test
    void 사용자는_룸을_싫어요_할_수_있다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room room = roomRepository.save(new Room(new MediaSource("lQcnNPqy2Ww")));

        roomService.dislike(member.getId(), room.getId());

        assertThat(member.getDislikes().getDislikeRooms()).isNotEmpty();
    }

    @Test
    void 사용자는_이미_싫어요한_룸을_싫어요할_수_없다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room room = roomRepository.save(new Room(new MediaSource("lQcnNPqy2Ww")));

        roomService.dislike(member.getId(), room.getId());

        assertThatThrownBy(() -> roomService.dislike(member.getId(), room.getId()))
                .isInstanceOf(AlreadyDislikeException.class)
                .hasMessageContaining("이미 싫어요한 룸입니다.");
    }

    @Test
    void 사용자는_스크랩한_룸을_싫어요할_수_없다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room room = roomRepository.save(new Room(new MediaSource("lQcnNPqy2Ww")));

        roomService.scrap(member.getId(), room.getId());

        assertThatThrownBy(() -> roomService.dislike(member.getId(), room.getId()))
                .isInstanceOf(AlreadyScrappedException.class)
                .hasMessageContaining("이미 스크랩된 룸입니다.");
    }

    @Test
    void 사용자는_싫어요한_룸을_취소할_수_있다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room room = roomRepository.save(new Room(new MediaSource("lQcnNPqy2Ww")));
        roomService.dislike(member.getId(), room.getId());

        roomService.undislike(member.getId(), room.getId());

        assertThat(member.getDislikes().getDislikeRooms()).isEmpty();
    }

    @Test
    void 사용자는_싫어요하지_않은_룸을_취소할_수_없다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room room = roomRepository.save(new Room(new MediaSource("lQcnNPqy2Ww")));

        assertThatThrownBy(() -> roomService.undislike(member.getId(), room.getId()))
                .isInstanceOf(NotDislikedException.class)
                .hasMessageContaining("싫어요하지 않은 룸입니다.");
    }
}
