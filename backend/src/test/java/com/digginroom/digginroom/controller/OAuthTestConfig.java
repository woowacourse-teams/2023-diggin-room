package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.service.OAuthUsernameResolver;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class OAuthTestConfig {

    @Bean
    @Primary
    public OAuthUsernameResolver oAuthUsernameResolver() {
        return (idToken) -> "username";
    }
}
