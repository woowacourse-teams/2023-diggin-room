package com.digginroom.digginroom.room.repository;

import static com.digginroom.digginroom.TestFixture.나무;
import static org.assertj.core.api.Assertions.assertThat;

import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.repository.RoomRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void Jpa는_엔티티의_동등성을_재정의하지_않는다(@Autowired EntityManager entityManager) {
        Room 나무 = roomRepository.save(나무());

        entityManager.clear();
        var found = roomRepository.findById(나무.getId());

        assertThat(found).get().isNotEqualTo(나무);
    }
}
