package com.digginroom.digginroom.oauth.cache;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.digginroom.digginroom.oauth.payload.Issuer.GOOGLE;
import static com.digginroom.digginroom.oauth.payload.Issuer.KAKAO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CachedJwkProvidersTest {

    private final CachedJwkProviders cachedJwkProviders = new CachedJwkProviders();

    @Test
    void JwkProvider_인스턴스들을_잘_초기화한다() {
        assertThatNoException()
                .isThrownBy(() -> new CachedJwkProviders());
    }

    @Test
    void 캐시된_JwkProvider의_인스턴스가_반환된다() {
        assertThat(cachedJwkProviders.getJwkProviderFor(KAKAO))
                .isEqualTo(cachedJwkProviders.getJwkProviderFor(KAKAO));
        assertThat(cachedJwkProviders.getJwkProviderFor(GOOGLE))
                .isEqualTo(cachedJwkProviders.getJwkProviderFor(GOOGLE));
    }

    @Test
    void ISSUER에_맞는_JwkProvider를_반환한다() {
        assertThat(cachedJwkProviders.getJwkProviderFor(KAKAO))
                .isNotEqualTo(cachedJwkProviders.getJwkProviderFor(GOOGLE));
    }
}
