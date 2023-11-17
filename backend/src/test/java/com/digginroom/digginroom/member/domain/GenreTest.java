package com.digginroom.digginroom.member.domain;

import static com.digginroom.digginroom.domain.Genre.of;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.digginroom.digginroom.exception.GenreException.GenreNotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GenreTest {

    @Test
    void 존재하지_않는_장르를_입력하면_예외가_발생한다() {
        assertThatThrownBy(() -> of("없는 장르"))
                .isInstanceOf(GenreNotFoundException.class);
    }
}
