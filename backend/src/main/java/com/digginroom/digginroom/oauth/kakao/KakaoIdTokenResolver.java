package com.digginroom.digginroom.oauth.kakao;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.digginroom.digginroom.oauth.OAuthIdTokenResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoIdTokenResolver implements OAuthIdTokenResolver {

    private final KakaoIdTokenVerifier kakaoIdTokenVerifier;

    @Override
    public String resolve(final String idToken) {
        DecodedJWT jwt = kakaoIdTokenVerifier.verify(idToken);
        return jwt.getClaims().get("sub").asString();
    }
}
