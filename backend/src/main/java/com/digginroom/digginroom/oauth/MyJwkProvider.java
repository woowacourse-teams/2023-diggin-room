package com.digginroom.digginroom.oauth;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.digginroom.digginroom.domain.Provider;
import com.digginroom.digginroom.exception.OAuthResolverException.InvalidJwkUrlException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public record MyJwkProvider(JwkProvider jwkProvider, Provider provider) {

    public static MyJwkProvider of(String url, Provider provider) {
        try {
            JwkProvider jwk = new JwkProviderBuilder(new URL(url))
                    .cached(10, 7, TimeUnit.DAYS)
                    .build();

            return new MyJwkProvider(jwk, provider);
        } catch (MalformedURLException e) {
            throw new InvalidJwkUrlException();
        }
    }

    public boolean support(Provider provider) {
        return this.provider.equals(provider);
    }
}
