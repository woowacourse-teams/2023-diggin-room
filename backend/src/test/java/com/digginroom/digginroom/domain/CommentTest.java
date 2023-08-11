package com.digginroom.digginroom.domain;

import static com.digginroom.digginroom.controller.TestFixture.파워;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class CommentTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    void 댓글은_현재시간으로_부터_생성시간과의_초_차이를_계산한다() {
        String context = "이 룸 정말 좋네요";
        Comment comment = new Comment(1L, context, 파워());
        entityManager.persist(comment);

        Long elapsedTime = comment.getElapsedTime();

        assertThat(elapsedTime).isGreaterThanOrEqualTo(0L);
    }
}
