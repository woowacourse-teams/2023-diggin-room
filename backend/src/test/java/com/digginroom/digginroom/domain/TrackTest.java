package com.digginroom.digginroom.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.digginroom.digginroom.TestFixture;
import com.digginroom.digginroom.domain.room.Genre;
import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.domain.track.Track;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TrackTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 서브장르가_컨버터를_통해서_문자열로_변환된다() {
        Track track = Track.builder()
                .artist("코건")
                .title("Lost Child")
                .superGenre(Genre.ROCK)
                .subGenres(List.of("Alternative Rock", "Noise Rock"))
                .description("코건은 코건")
                .build();
        Room room = new Room(TestFixture.나무().getIdentifier(), track);

        em.persist(room);
        em.flush();

        String subGenre = jdbcTemplate.queryForObject(
                "select sub_genres from room where id=?",
                String.class,
                room.getId()
        );
        assertThat(subGenre).isEqualTo("[\"Alternative Rock\",\"Noise Rock\"]");
    }
}
