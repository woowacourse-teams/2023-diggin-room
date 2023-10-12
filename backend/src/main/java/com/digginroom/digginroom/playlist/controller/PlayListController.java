package com.digginroom.digginroom.playlist.controller;

import com.digginroom.digginroom.playlist.service.GoogleAuthService;
import com.digginroom.digginroom.playlist.service.dto.PlayListRequest;
import com.digginroom.digginroom.playlist.service.dto.PlayListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class PlayListController {

    private final GoogleAuthService googleAuthService;

    @PostMapping("/playList")
    public ResponseEntity<PlayListResponse> makePlayList(
            @RequestBody PlayListRequest playListRequest
    ) {
        return ResponseEntity.ok().body(googleAuthService.makePlayList(playListRequest));
    }
}
