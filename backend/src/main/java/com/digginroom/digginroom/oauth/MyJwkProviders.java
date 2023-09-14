package com.digginroom.digginroom.oauth;

import com.auth0.jwk.JwkProvider;
import com.digginroom.digginroom.domain.member.Provider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyJwkProviders {

    private final List<MyJwkProvider> myJwkProviders;

    public JwkProvider getJwkProvider(Provider provider) {
        return myJwkProviders.stream()
                .filter(myJwkProvider -> myJwkProvider.support(provider))
                .map(MyJwkProvider::jwkProvider)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
