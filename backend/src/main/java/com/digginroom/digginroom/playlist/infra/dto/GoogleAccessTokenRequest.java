package com.digginroom.digginroom.playlist.infra.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GoogleAccessTokenRequest(
        String clientId,
        String clientSecret,
        String code,
        String redirectUri,
        String grantType
) {
}
