package com.digginroom.digginroom.playlist.infra;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "oauth.google")
public class GoogleOAuthConfig {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String grantType;
}
