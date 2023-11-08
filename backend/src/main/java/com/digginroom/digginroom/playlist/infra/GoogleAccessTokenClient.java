package com.digginroom.digginroom.playlist.infra;

import com.digginroom.digginroom.playlist.infra.dto.GoogleAccessTokenRequest;
import com.digginroom.digginroom.playlist.infra.dto.GoogleAccessTokenResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GoogleAccessTokenClient {

    private WebClient webClient = WebClient.builder()
            .baseUrl("https://oauth2.googleapis.com")
            .build();

    public GoogleAccessTokenResponse getGoogleToken(final GoogleAccessTokenRequest request) {
        return webClient.post()
                .uri("/token")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GoogleAccessTokenResponse.class)
                .block();
    }
}
