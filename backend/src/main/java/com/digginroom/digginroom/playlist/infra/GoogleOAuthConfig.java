package com.digginroom.digginroom.playlist.infra;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.google")
public record GoogleOAuthConfig(
        String clientId,
        String clientSecret,
        String redirectUri,
        String grantType
) {
}
