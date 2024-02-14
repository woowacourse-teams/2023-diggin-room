package com.digginroom.digginroom.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.digginroom.digginroom.domain.member.Member;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberTest {

    @Test
    void Username이_guest로_시작하는_게스트를_생성한다() {
        Member member = Member.guest();

        assertThat(member.getUsername()).startsWith("guest-");
    }

    @Test
    void 게스트의_비밀번호는_없다() {
        Member member = Member.guest();

        assertThat(member.getPassword().isEmpty()).isTrue();
    }
}
