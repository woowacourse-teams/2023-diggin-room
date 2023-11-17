package com.digginroom.digginroom.member.domain.password;

import static org.assertj.core.api.Assertions.assertThat;

import com.digginroom.digginroom.util.HashAlgorithm;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HashAlgorithmTest {

    @Test
    void SHA256으로_해싱한다() {
        String text = "땡칠";

        String encrypted = HashAlgorithm.encrypt(text);
        assertThat(encrypted).hasSize(64);
    }

    @Test
    void 해시는_16진수로_구성된다() {
        String hexadecimalPattern = "^[0-9A-Fa-f]+$";
        String text = "땡칠";

        String encrypted = HashAlgorithm.encrypt(text);
        assertThat(encrypted).containsPattern(hexadecimalPattern);
    }
}
