package com.digginroom.digginroom.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.digginroom.digginroom.service.CommentService;
import com.digginroom.digginroom.service.dto.CommentRequest;
import com.digginroom.digginroom.service.dto.CommentResponse;
import com.digginroom.digginroom.service.dto.CommentsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{roomId}/comments")
    public ResponseEntity<CommentsResponse> findRoomComments(
            @Auth final Long loginMemberId,
            @PathVariable final Long roomId
    ) {
        CommentsResponse roomComments = commentService.getRoomComments(roomId, loginMemberId);
        return ResponseEntity.status(OK).body(roomComments);
    }

    @PostMapping("/{roomId}/comments")
    public ResponseEntity<CommentResponse> comment(
            @Auth final Long loginMemberId,
            @PathVariable final Long roomId,
            @Valid @RequestBody final CommentRequest request
    ) {
        CommentResponse response = commentService.comment(roomId, loginMemberId, request);
        return ResponseEntity.status(CREATED).body(response);
    }

    @PatchMapping("/{roomId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> update(
            @Auth final Long loginMemberId,
            @PathVariable final Long roomId,
            @PathVariable final Long commentId,
            @Valid @RequestBody final CommentRequest request
    ) {
        CommentResponse response = commentService.update(loginMemberId, commentId, request);
        return ResponseEntity.status(OK).body(response);
    }

    @DeleteMapping("/{roomId}/comments/{commentId}")
    public ResponseEntity<Void> delete(
            @Auth final Long loginMemberId,
            @PathVariable final Long roomId,
            @PathVariable final Long commentId
    ) {
        commentService.delete(loginMemberId, commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<String> findRoomComments() {
        return ResponseEntity.ok().body("새로운 버전");
    }
}
