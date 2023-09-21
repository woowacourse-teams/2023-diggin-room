package com.digginroom.digginroom.oauth.jwk;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.digginroom.digginroom.domain.member.Provider;
import com.digginroom.digginroom.exception.OAuthResolverException.InvalidIdTokenException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class GoogleJwkProvider implements ThirdPartyJwkProvider {

    private final JwkProvider jwkProvider;

    public GoogleJwkProvider() {
        this.jwkProvider = new JwkProviderBuilder(JwkUrl.GOOGLE.get())
                .cached(10, 7, TimeUnit.DAYS)
                .build();
    }

    public Jwk getJwkBy(final String keyId) {
        try {
            return jwkProvider.get(keyId);
        } catch (JwkException e) {
            throw new InvalidIdTokenException();
        }
    }

    public boolean supports(final Provider provider) {
        return Objects.equals(Provider.GOOGLE, provider);
    }
}
