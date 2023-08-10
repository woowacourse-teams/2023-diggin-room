package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.controller.dto.CommentRequest;
import com.digginroom.digginroom.controller.dto.CommentResponse;
import com.digginroom.digginroom.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class CommentController {

    private final RoomService roomService;

    @PostMapping("/{roomId}/comments")
    public ResponseEntity<CommentResponse> comment(
            @Auth final Long memberId,
            @PathVariable final Long roomId,
            @RequestBody final CommentRequest request
    ) {
        CommentResponse response = roomService.comment(roomId, memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
