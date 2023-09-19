package com.digginroom.digginroom.oauth.cache;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.digginroom.digginroom.domain.member.Provider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@Getter
@RequiredArgsConstructor
public class CachedJwkProviderFactory {
    
    public static JwkProvider of(final Provider oAuthProvider) {
        return new JwkProviderBuilder(JwkUrl.of(oAuthProvider))
                .cached(10, 7, TimeUnit.DAYS)
                .build();
    }
}
