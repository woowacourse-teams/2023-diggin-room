package com.digginroom.digginroom.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MediaSourceTest {

    // TODO: 2023/07/17 추후 커스텀 예외로 변경
    @Test
    void 미디어_타입은_비어선_안된다() {
        assertThatThrownBy(() -> new MediaSource(null, "lQcnNPqy2Ww"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("미디어 타입이 지정되지 않았습니다");
    }

    @Test
    void 식별자는_비어선_안된다() {
        assertThatThrownBy(() -> new MediaSource(MediaType.YOUTUBE, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("식별자가 지정되지 않았습니다");
    }

    @Test
    void 식별자는_공백일_수_없다() {
        assertThatThrownBy(() -> new MediaSource(MediaType.YOUTUBE, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("식별자는 공백일 수 없습니다");
    }
}
