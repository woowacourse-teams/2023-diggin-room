package com.digginroom.digginroom.oauth;

import com.digginroom.digginroom.domain.member.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CachedJwkProviderConfig {

    @Bean
    public CachedJwkProvider kakao(){
        return CachedJwkProvider.of("https://kauth.kakao.com/.well-known/jwks.json", Provider.KAKAO);
    }

    @Bean
    public CachedJwkProvider google(){
        return CachedJwkProvider.of("https://www.googleapis.com/oauth2/v3/certs", Provider.GOOGLE);
    }
}
