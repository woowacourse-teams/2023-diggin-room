package com.digginroom.digginroom.oauth;

import com.digginroom.digginroom.oauth.jwk.ThirdPartyJwkProviders;
import com.digginroom.digginroom.oauth.payload.IdTokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdTokenResolver {

    private final ThirdPartyJwkProviders thirdPartyJwkProviders;

    public IdTokenPayload resolve(final String rawIdToken) {
        thirdPartyJwkProviders.verify(rawIdToken);
        return thirdPartyJwkProviders.resolve(rawIdToken);
    }
}
