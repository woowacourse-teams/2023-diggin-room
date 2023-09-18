package com.digginroom.digginroom.oauth;

import com.auth0.jwk.JwkProvider;
import com.digginroom.digginroom.domain.member.Provider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CachedJwkProviders {

    private final List<CachedJwkProvider> cachedJwkProviders;

    public JwkProvider getJwkProvider(Provider provider) {
        return cachedJwkProviders.stream()
                .filter(cachedJwkProvider -> cachedJwkProvider.support(provider))
                .map(CachedJwkProvider::jwkProvider)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
