package com.digginroom.digginroom.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PasswordTest {

    private static final String PLAIN_TEXT = "konghanapassword";

    @Test
    void 패스워드를_직접_생성하면_암호화된_패스워드가_생성된다() {
        Password password = new Password(PLAIN_TEXT);

        assertThat(password.getPassword()).isNotEqualTo(PLAIN_TEXT);
    }

    @Test
    void 빈_패스워드인지_확인할_수_있다() {
        assertThat(new Password(PLAIN_TEXT).isEmpty()).isFalse();
        assertThat(Password.EMPTY.isEmpty()).isTrue();
    }

    @Test
    void 패스워드가_일치하는지_확인할_수_있다() {
        Password password = new Password(PLAIN_TEXT);

        assertThat(password.doesNotMatch(PLAIN_TEXT)).isFalse();
    }

    @Test
    void 패스워드가_일치하지_않는지_확인할_수_있다() {
        Password password = new Password(PLAIN_TEXT);

        assertThat(password.doesNotMatch("anotherpassword")).isTrue();
    }
}
