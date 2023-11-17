package com.digginroom.digginroom.member.domain.member;

import com.digginroom.digginroom.domain.member.Provider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.digginroom.digginroom.TestFixture.ID_TOKEN_ISSUER_GOOGLE;
import static com.digginroom.digginroom.TestFixture.ID_TOKEN_ISSUER_KAKAO;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProviderTest {

    @Test
    void URL에_맞는_프로바이더를_찾는다() {
        Assertions.assertThat(Provider.of(ID_TOKEN_ISSUER_GOOGLE)).get().isEqualTo(Provider.GOOGLE);
        assertThat(Provider.of(ID_TOKEN_ISSUER_KAKAO)).get().isEqualTo(Provider.KAKAO);
    }

    @Test
    void 지원하지_않는_ISSUER를_대입하면_프로바이더를_못찾는다() {
        assertThat(Provider.of("https://digginroom.site")).isEmpty();
    }
}
