package com.digginroom.digginroom.oauth;

import com.auth0.jwk.JwkProvider;
import com.digginroom.digginroom.domain.member.Provider;
import com.digginroom.digginroom.exception.OAuthResolverException.UnsupportedProviderException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CachedJwkProviders {

    private final List<CachedJwkProvider> cachedJwkProviders;

    public JwkProvider getJwkProvider(final Provider provider) {
        return cachedJwkProviders.stream()
                .filter(cachedJwkProvider -> cachedJwkProvider.support(provider))
                .map(CachedJwkProvider::getJwkProvider)
                .findFirst()
                .orElseThrow(() -> new UnsupportedProviderException(provider.name()));
    }
}
