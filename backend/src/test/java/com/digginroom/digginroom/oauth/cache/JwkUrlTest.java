package com.digginroom.digginroom.oauth.cache;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.digginroom.digginroom.domain.member.Provider;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JwkUrlTest {

    @Test
    void OAuth_프로바이더에_맞는_JWK_URL을_반환한다() throws MalformedURLException {
        assertThat(JwkUrl.of(Provider.GOOGLE))
                .isEqualTo(new URL("https://www.googleapis.com/oauth2/v3/certs"));
        assertThat(JwkUrl.of(Provider.KAKAO))
                .isEqualTo(new URL("https://kauth.kakao.com/.well-known/jwks.json"));
    }
}
