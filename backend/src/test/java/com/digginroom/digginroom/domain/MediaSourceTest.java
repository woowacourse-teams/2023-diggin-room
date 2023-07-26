package com.digginroom.digginroom.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.digginroom.digginroom.exception.MediaSourceException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MediaSourceTest {

    @Test
    void 식별자는_비어선_안된다() {
        assertThatThrownBy(() -> new MediaSource(null))
                .isInstanceOf(MediaSourceException.NoIdentifierException.class);
    }

    @Test
    void 식별자는_공백일_수_없다() {
        assertThatThrownBy(() -> new MediaSource(""))
                .isInstanceOf(MediaSourceException.NoIdentifierException.class);
    }
}
