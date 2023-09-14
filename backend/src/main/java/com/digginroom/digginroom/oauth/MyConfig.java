package com.digginroom.digginroom.oauth;

import com.digginroom.digginroom.domain.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public MyJwkProvider kakao(){
        return MyJwkProvider.of("https://kauth.kakao.com/.well-known/jwks.json", Provider.KAKAO);
    }

    @Bean
    public MyJwkProvider google(){
        return MyJwkProvider.of("https://www.googleapis.com/oauth2/v3/certs", Provider.GOOGLE);
    }
}
