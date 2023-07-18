package com.digginroom.digginroom.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.digginroom.digginroom.domain.MediaSource;
import com.digginroom.digginroom.domain.MediaType;
import com.digginroom.digginroom.domain.Room;
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

    @Test
    void 여러_룸_중_랜덤으로_하나를_선택한다() {
        Room 나무 = new Room(new MediaSource(MediaType.YOUTUBE, "lQcnNPqy2Ww"));
        Room 가까운듯먼그대여 = new Room(new MediaSource(MediaType.YOUTUBE, "2VkWaOOF4Rc"));
        roomRepository.save(나무);
        roomRepository.save(가까운듯먼그대여);

        final var pickedRoom = roomService.pickRandom();

        assertThat(pickedRoom).isNotNull();
    }

    @Test
    void 페이징을_사용하여_여러_룸_중_랜덤으로_하나를_선택한다() {
        Room 나무 = new Room(new MediaSource(MediaType.YOUTUBE, "lQcnNPqy2Ww"));
        Room 가까운듯먼그대여 = new Room(new MediaSource(MediaType.YOUTUBE, "2VkWaOOF4Rc"));
        roomRepository.save(나무);
        roomRepository.save(가까운듯먼그대여);

        final var pickedRoom = roomService.pickRandomByPage();

        assertThat(pickedRoom).isNotNull();
    }
}
