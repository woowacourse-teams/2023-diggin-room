package com.digginroom.digginroom.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.digginroom.digginroom.domain.MediaSource;
import com.digginroom.digginroom.domain.Room;
import jakarta.persistence.EntityManager;
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
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void 룸_저장_시_미디어_소스도_같아_저장된다() {
        Room 나무 = new Room(new MediaSource("lQcnNPqy2Ww"));

        roomRepository.save(나무);

        assertThat(나무.getMediaSource().getId()).isNotNull();
    }

    @Test
    void Jpa는_엔티티의_동등성을_재정의하지_않는다(@Autowired EntityManager entityManager) {
        Room 나무 = new Room(new MediaSource("lQcnNPqy2Ww"));
        roomRepository.save(나무);

        entityManager.clear();
        var found = roomRepository.findById(나무.getId());

        assertThat(found).get().isNotEqualTo(나무);
    }
}
