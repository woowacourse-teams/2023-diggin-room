package com.digginroom.digginroom.oauth.payload;

import com.auth0.jwt.interfaces.Claim;
import com.digginroom.digginroom.domain.member.Provider;

import java.util.Map;

public class KakaoIdTokenPayload implements IdTokenPayload {

    private final Map<String, Claim> claims;

    public KakaoIdTokenPayload(final Map<String, Claim> claims) {
        this.claims = claims;
    }

    @Override
    public String getNickname() {
        return claims.get("nickname").asString();
    }

    @Override
    public String getUsername() {
        return claims.get("sub").asString();
    }

    @Override
    public Provider getProvider() {
        return Provider.KAKAO;
    }
}
