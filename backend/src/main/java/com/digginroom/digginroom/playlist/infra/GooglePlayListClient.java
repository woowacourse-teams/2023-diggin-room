package com.digginroom.digginroom.playlist.infra;

import com.digginroom.digginroom.exception.PlayListException.PlaylistCreateException;
import com.digginroom.digginroom.playlist.infra.dto.YouTubePlayListRequest;
import com.digginroom.digginroom.playlist.infra.dto.YouTubePlayListResponse;
import com.digginroom.digginroom.playlist.infra.dto.YoutubePlayListItemRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GooglePlayListClient {

    private WebClient webClient = WebClient.builder()
            .baseUrl("https://www.googleapis.com/youtube/v3/")
            .build();

    public YouTubePlayListResponse createPlayList(final String authorization, final YouTubePlayListRequest request) {
        return webClient.post()
                .uri("/playlists?part=id,snippet")
                .headers(headers -> headers.setBearerAuth(authorization))
                .bodyValue(request)
                .exchangeToMono(this::generateYoutubePlayListResponse)
                .block();
    }

    public Mono<YouTubePlayListResponse> generateYoutubePlayListResponse(final ClientResponse response) {
        if (response.statusCode().equals(HttpStatus.OK)) {
            return response.bodyToMono(YouTubePlayListResponse.class);
        }
        throw new PlaylistCreateException();
    }

    public Integer insertPlayListItems(final String authorization, final YoutubePlayListItemRequest request) {
        return webClient.post()
                .uri("/playlistItems?part=contentDetails,id,snippet,status")
                .headers(headers -> headers.setBearerAuth(authorization))
                .bodyValue(request)
                .exchangeToMono(this::exchangeSuccessCount)
                .block();
    }

    private Mono<Integer> exchangeSuccessCount(final ClientResponse response) {
        if (response.statusCode().equals(HttpStatus.OK)) {
            return Mono.just(1);
        }
        return Mono.just(0);
    }
}
