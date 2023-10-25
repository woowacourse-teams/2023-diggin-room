package com.digginroom.digginroom.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.digginroom.digginroom.exception.CommentException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void 댓글_정보가_없는_경우_에러가_발생한다() {
        assertThatThrownBy(() -> commentRepository.getCommentById(Long.MAX_VALUE))
                .isInstanceOf(CommentException.NotFoundException.class);
    }
}
