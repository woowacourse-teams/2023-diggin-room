package com.digginroom.digginroom.oauth.cache;

import com.auth0.jwk.JwkProvider;
import com.digginroom.digginroom.domain.member.Provider;
import com.digginroom.digginroom.exception.OAuthResolverException;
import com.digginroom.digginroom.oauth.payload.Issuer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CachedJwkProviders {

    private static final Map<Issuer, JwkProvider> JWK_PROVIDERS = Map.of(
            Issuer.GOOGLE, CachedJwkProviderFactory.of(Provider.GOOGLE),
            Issuer.KAKAO, CachedJwkProviderFactory.of(Provider.KAKAO)
    );

    public JwkProvider getJwkProviderFor(final Issuer issuer) {
        validateHasJwkProviderFor(issuer);
        return JWK_PROVIDERS.get(issuer);
    }

    private void validateHasJwkProviderFor(final Issuer issuer) {
        if (!JWK_PROVIDERS.containsKey(issuer)) {
            throw new OAuthResolverException.UnsupportedIdTokenException(issuer.name());
        }
    }
}
