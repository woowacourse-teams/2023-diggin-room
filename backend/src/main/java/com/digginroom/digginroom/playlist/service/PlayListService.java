package com.digginroom.digginroom.playlist.service;

import com.digginroom.digginroom.playlist.infra.GoogleAccessTokenClient;
import com.digginroom.digginroom.playlist.infra.GoogleOAuthConfig;
import com.digginroom.digginroom.playlist.infra.GooglePlayListClient;
import com.digginroom.digginroom.playlist.infra.dto.GoogleAccessTokenRequest;
import com.digginroom.digginroom.playlist.infra.dto.YouTubePlayListRequest;
import com.digginroom.digginroom.playlist.infra.dto.YouTubePlayListRequest.Snippet;
import com.digginroom.digginroom.playlist.infra.dto.YoutubePlayListItemRequest;
import com.digginroom.digginroom.playlist.infra.dto.YoutubePlayListItemRequest.Snippet.ResourceId;
import com.digginroom.digginroom.playlist.service.dto.PlayListRequest;
import com.digginroom.digginroom.playlist.service.dto.PlayListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayListService {

    private final GoogleOAuthConfig googleOAuthConfig;
    private final GoogleAccessTokenClient googleAccessTokenClient;
    private final GooglePlayListClient playListClient;

    public PlayListResponse makePlayList(final PlayListRequest request) {
        String accessToken = getAccessToken(request.code());
        String playListId = createPlayList(accessToken, request);
        Integer success = insertPlayListItems(accessToken, playListId, request);
        return new PlayListResponse(calculateRequestCount(request), success);
    }

    private String getAccessToken(final String code) {
        GoogleAccessTokenRequest request = GoogleAccessTokenRequest.builder()
                .redirectUri(googleOAuthConfig.redirectUri())
                .clientId(googleOAuthConfig.clientId())
                .clientSecret(googleOAuthConfig.clientSecret())
                .grantType(googleOAuthConfig.grantType())
                .code(code)
                .build();

        return googleAccessTokenClient.getGoogleToken(request).accessToken();
    }

    private String createPlayList(final String authorization, final PlayListRequest request) {
        YouTubePlayListRequest youTubePlayListRequest = new YouTubePlayListRequest(
                new Snippet(request.title())
        );
        return playListClient.createPlayList(authorization, youTubePlayListRequest).id();
    }

    private Integer insertPlayListItems(
            final String authorization,
            final String playListId,
            final PlayListRequest request
    ) {
        List<String> videoIds = request.videoIds();
        return videoIds.stream()
                .map(videoId -> playListClient.insertPlayListItems(authorization, toRequest(playListId, videoId)))
                .reduce(0, Integer::sum);
    }

    private YoutubePlayListItemRequest toRequest(final String playListId, final String videoId) {
        return new YoutubePlayListItemRequest(
                new YoutubePlayListItemRequest.Snippet(playListId,
                        new ResourceId("youtube#video", videoId)
                )
        );
    }

    private int calculateRequestCount(final PlayListRequest request) {
        return request.videoIds().size();
    }
}
