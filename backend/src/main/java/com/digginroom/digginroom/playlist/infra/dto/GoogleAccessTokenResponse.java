package com.digginroom.digginroom.playlist.infra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleAccessTokenResponse(
        @JsonProperty("access_token")
        String accessToken
) {
}
