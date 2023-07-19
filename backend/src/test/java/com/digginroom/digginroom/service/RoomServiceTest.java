package com.digginroom.digginroom.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.digginroom.digginroom.domain.MediaSource;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.domain.Room;
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

        final var pickedRoom = roomService.pickRandom(member.getId());

        assertThat(pickedRoom).isNotNull();
    }

    @Test
    void 멤버는_룸을_스크랩할_수_있다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room room = roomRepository.save(new Room(new MediaSource("lQcnNPqy2Ww")));

        roomService.scrap(member.getId(), room.getId());

        assertThat(member.getScraps()).contains(room);
    }

    @Test
    void 이미_스크랩된_룸은_다시_스크랩되지_않는다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room room = roomRepository.save(new Room(new MediaSource("lQcnNPqy2Ww")));

        roomService.scrap(member.getId(), room.getId());
        roomService.scrap(member.getId(), room.getId());

        assertThat(member.getScraps()).hasSize(1);
    }

    @Test
    void 멤버는_룸스크랩을_취소할_수_있다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room room = roomRepository.save(new Room(new MediaSource("lQcnNPqy2Ww")));
        roomService.scrap(member.getId(), room.getId());

        roomService.unscrap(member.getId(), room.getId());

        assertThat(member.getScraps()).isEmpty();
    }

    @Test
    void 이미_스크랩_취소된_룸은_다시_취소되지_않는다() {
        Member member = memberRepository.save(new Member("member", "1234"));
        Room room = roomRepository.save(new Room(new MediaSource("lQcnNPqy2Ww")));
        Room otherRoom = roomRepository.save(new Room(new MediaSource("lQcnNPqy3Ww")));

        roomService.scrap(member.getId(), room.getId());
        roomService.scrap(member.getId(), otherRoom.getId());

        roomService.unscrap(member.getId(), room.getId());
        roomService.unscrap(member.getId(), room.getId());

        assertThat(member.getScraps()).hasSize(1);
    }
}
