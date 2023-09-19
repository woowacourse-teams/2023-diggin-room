package com.digginroom.digginroom.oauth.payload;

import com.digginroom.digginroom.domain.member.Provider;
import com.digginroom.digginroom.exception.OAuthResolverException.UnsupportedIdTokenException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.digginroom.digginroom.TestFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class IssuerTest {

    @Test
    void URL에_맞는_ISSUER를_찾는다() {
        assertThat(Issuer.of(ID_TOKEN_ISSUER_GOOGLE)).isEqualTo(Issuer.GOOGLE);
        assertThat(Issuer.of(ID_TOKEN_ISSUER_KAKAO)).isEqualTo(Issuer.KAKAO);
    }

    @Test
    void 지원하지_않는_ISSUER를_대입하면_예외가_발생한다() {
        assertThatException().isThrownBy(() -> Issuer.of("https://digginroom.site"))
                .isInstanceOf(UnsupportedIdTokenException.class);
    }

    @Test
    void IdToken_Issuer_GOOGLE에_알맞는_Payload를_생성한다() {
        IdTokenPayload payload = Issuer.GOOGLE.resolve(ID_TOKEN_SAMPLE_GOOGLE);

        assertThat(payload.getProvider()).isEqualTo(Provider.GOOGLE);
        assertThat(payload.getUsername()).isEqualTo("104106003661228920371");
        assertThat(payload.getNickname()).isEqualTo("김진욱");
    }

    @Test
    void IdToken_Issuer_KAKAO에_알맞는_Payload를_생성한다() {
        IdTokenPayload payload = Issuer.KAKAO.resolve(ID_TOKEN_SAMPLE_KAKAO);

        assertThat(payload.getProvider()).isEqualTo(Provider.KAKAO);
        assertThat(payload.getUsername()).isEqualTo("kakaouserid");
        assertThat(payload.getNickname()).isEqualTo("kakaonickname");
    }
}
