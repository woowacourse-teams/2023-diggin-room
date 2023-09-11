package com.digginroom.digginroom.oauth.kakao;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoOauthConfig {

    //TODO: 지금 코드로는 확장이 힘들다.
    @Bean
    public JwkProvider getJwtProvider(){
        return new JwkProviderBuilder("https://kauth.kakao.com")
                .cached(10, 7, TimeUnit.DAYS)
                .build();
    }
}
