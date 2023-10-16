package com.digginroom.digginroom.playlist.controller;

import com.digginroom.digginroom.playlist.service.PlayListService;
import com.digginroom.digginroom.playlist.service.dto.PlayListRequest;
import com.digginroom.digginroom.playlist.service.dto.PlayListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PlayListController {

    private final PlayListService playListService;

    @PostMapping("/playlist")
    public ResponseEntity<PlayListResponse> makePlayList(@RequestBody PlayListRequest playListRequest) {
        return ResponseEntity.ok().body(playListService.makePlayList(playListRequest));
    }
}
