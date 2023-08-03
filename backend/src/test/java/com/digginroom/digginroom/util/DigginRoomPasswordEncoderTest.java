package com.digginroom.digginroom.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DigginRoomPasswordEncoderTest {

    DigginRoomPasswordEncoder digginRoomPasswordEncoder = new DigginRoomPasswordEncoder();

    @Test
    void 비밀번호를_암호화한다() {
        String plainText = "파워";

        String encoded = digginRoomPasswordEncoder.encode(plainText);

        assertThat(encoded).hasSize(100);
        assertThat(encoded).doesNotContain(plainText);
    }

    @Test
    void 비밀번호가_올바른지_확인한다() {
        String plainText = "파워";
        String encoded = digginRoomPasswordEncoder.encode(plainText);

        assertThat(digginRoomPasswordEncoder.matches(plainText, encoded)).isTrue();
    }
}
