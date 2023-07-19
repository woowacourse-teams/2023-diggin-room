package com.digginroom.digginroom.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.digginroom.digginroom.exception.RoomException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class RoomTest {

    @Test
    void 미디어소스는_필수다() {
        assertThatThrownBy(() -> new Room(null))
                .isInstanceOf(RoomException.NoMediaSourceException.class);
    }
}
