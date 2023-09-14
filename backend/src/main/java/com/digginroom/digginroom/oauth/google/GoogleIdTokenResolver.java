package com.digginroom.digginroom.oauth.google;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.digginroom.digginroom.oauth.OAuthIdTokenResolver;
import org.springframework.stereotype.Component;

@Component
public class GoogleIdTokenResolver implements OAuthIdTokenResolver {

    private final GoogleIdTokenVerifier googleIdTokenVerifier;

    public GoogleIdTokenResolver(final GoogleIdTokenVerifier googleIdTokenVerifier) {
        this.googleIdTokenVerifier = googleIdTokenVerifier;
    }

    @Override
    public String resolve(final String idToken) {
        DecodedJWT jwt = googleIdTokenVerifier.verify(idToken);
        return jwt.getClaims().get("sub").asString();
    }
}
