package com.digginroom.digginroom.playlist.infra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record GoogleAccessTokenRequest(
        @JsonProperty("client_id")
        String clientId,
        @JsonProperty("client_secret")
        String clientSecret,
        String code,
        @JsonProperty("redirect_uri")
        String redirectUri,
        @JsonProperty("grant_type")
        String grantType
) {
}
