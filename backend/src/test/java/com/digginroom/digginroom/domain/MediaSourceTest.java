package com.digginroom.digginroom.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MediaSourceTest {

    @Test
    void 식별자는_비어선_안된다() {
        assertThatThrownBy(() -> new MediaSource(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("식별자가 지정되지 않았습니다");
    }

    @Test
    void 식별자는_공백일_수_없다() {
        assertThatThrownBy(() -> new MediaSource(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("식별자는 공백일 수 없습니다");
    }
}
