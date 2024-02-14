package com.digginroom.digginroom.oauth.jwk;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.digginroom.digginroom.domain.member.vo.Provider;
import com.digginroom.digginroom.exception.OAuthResolverException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class KakaoJwkProvider implements ThirdPartyJwkProvider {

    private final JwkProvider jwkProvider;

    public KakaoJwkProvider() {
        this.jwkProvider = new JwkProviderBuilder(JwkUrl.KAKAO.get())
                .cached(10, 7, TimeUnit.DAYS)
                .build();
    }

    @Override
    public Jwk getJwkBy(final String keyId) {
        try {
            return jwkProvider.get(keyId);
        } catch (JwkException e) {
            throw new OAuthResolverException.InvalidIdTokenException();
        }
    }

    public boolean supports(final Provider provider) {
        return Objects.equals(Provider.KAKAO, provider);
    }
}
