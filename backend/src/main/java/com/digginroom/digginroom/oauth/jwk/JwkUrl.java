package com.digginroom.digginroom.oauth.jwk;

import com.digginroom.digginroom.exception.OAuthResolverException;

import java.net.MalformedURLException;
import java.net.URL;

public enum JwkUrl {

    GOOGLE("https://www.googleapis.com/oauth2/v3/certs"),
    KAKAO("https://kauth.kakao.com/.well-known/jwks.json");

    private final String keyUrl;

    JwkUrl(final String keyUrl) {
        this.keyUrl = keyUrl;
    }

    public URL get() {
        try {
            return new URL(keyUrl);
        } catch (MalformedURLException e) {
            throw new OAuthResolverException.InvalidJwkUrlException();
        }
    }
}
