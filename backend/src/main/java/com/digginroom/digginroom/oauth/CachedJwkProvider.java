package com.digginroom.digginroom.oauth;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.digginroom.digginroom.domain.member.Provider;
import com.digginroom.digginroom.exception.OAuthResolverException.InvalidJwkUrlException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CachedJwkProvider {

    private final JwkProvider jwkProvider;
    private final Provider provider;

    public static CachedJwkProvider of(String url, Provider provider) {
        try {
            JwkProvider jwk = new JwkProviderBuilder(new URL(url))
                    .cached(10, 7, TimeUnit.DAYS)
                    .build();

            return new CachedJwkProvider(jwk, provider);
        } catch (MalformedURLException e) {
            throw new InvalidJwkUrlException();
        }
    }

    public boolean support(Provider provider) {
        return this.provider.equals(provider);
    }
}
