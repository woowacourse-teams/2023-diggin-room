package com.digginroom.digginroom.oauth.jwk;

import com.auth0.jwk.Jwk;
import com.digginroom.digginroom.domain.member.Provider;

public interface ThirdPartyJwkProvider {

    Jwk getJwkBy(final String keyId);

    boolean supports(final Provider provider);
}
