package com.digginroom.digginroom.oauth.cache;

import com.digginroom.digginroom.domain.member.Provider;
import com.digginroom.digginroom.exception.OAuthResolverException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class JwkUrl {

    private static final Map<Provider, String> JWK_URL_MAPPING = Map.of(
            Provider.GOOGLE, "https://www.googleapis.com/oauth2/v3/certs",
            Provider.KAKAO, "https://kauth.kakao.com/.well-known/jwks.json"
    );

    public static URL of(Provider oAuthProvider) {
        try {
            return new URL(JWK_URL_MAPPING.get(oAuthProvider));
        } catch (MalformedURLException e) {
            throw new OAuthResolverException.InvalidJwkUrlException();
        }
    }
}
