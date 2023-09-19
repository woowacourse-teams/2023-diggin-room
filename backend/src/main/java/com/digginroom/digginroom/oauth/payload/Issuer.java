package com.digginroom.digginroom.oauth.payload;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.digginroom.digginroom.exception.OAuthResolverException;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum Issuer {

    GOOGLE("https://accounts.google.com", GoogleIdTokenPayload::new),
    KAKAO("https://kauth.kakao.com", KakaoIdTokenPayload::new);

    private final String issuer;
    private final Function<Map<String, Claim>, IdTokenPayload> constructor;

    Issuer(String issuer, Function<Map<String, Claim>, IdTokenPayload> constructor) {
        this.issuer = issuer;
        this.constructor = constructor;
    }

    public static Issuer of(String issuer) {
        return Arrays.stream(values())
                .filter(it -> it.supports(issuer))
                .findFirst()
                .orElseThrow(() -> new OAuthResolverException.UnsupportedIdTokenException(issuer));
    }

    private boolean supports(String issuer) {
        return this.issuer.equals(issuer);
    }

    public IdTokenPayload resolve(String idToken) {
        return constructor.apply(JWT.decode(idToken).getClaims());
    }
}
