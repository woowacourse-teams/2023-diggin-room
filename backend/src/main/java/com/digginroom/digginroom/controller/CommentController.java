package com.digginroom.digginroom.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.digginroom.digginroom.controller.dto.CommentRequest;
import com.digginroom.digginroom.controller.dto.CommentResponse;
import com.digginroom.digginroom.controller.dto.CommentsResponse;
import com.digginroom.digginroom.service.RoomService;
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

    private final RoomService roomService;

    @GetMapping("/{roomId}/comments")
    public ResponseEntity<CommentsResponse> findRoomComments(
            @Auth final Long loginMemberId,
            @PathVariable final Long roomId
    ) {
        CommentsResponse roomComments = roomService.findRoomComments(roomId);
        return ResponseEntity.status(OK).body(roomComments);
    }

    @PostMapping("/{roomId}/comments")
    public ResponseEntity<CommentResponse> comment(
            @Auth final Long loginMemberId,
            @PathVariable final Long roomId,
            @Valid @RequestBody final CommentRequest request
    ) {
        CommentResponse response = roomService.comment(roomId, loginMemberId, request);
        return ResponseEntity.status(CREATED).body(response);
    }

    @PatchMapping("/{roomId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> update(
            @Auth final Long loginMemberId,
            @PathVariable final Long roomId,
            @PathVariable final Long commentId,
            @Valid @RequestBody final CommentRequest request
    ) {
        CommentResponse response = roomService.updateComment(roomId, loginMemberId, commentId, request);
        return ResponseEntity.status(OK).body(response);
    }

    @DeleteMapping("/{roomId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> delete(
            @Auth final Long loginMemberId,
            @PathVariable final Long roomId,
            @PathVariable final Long commentId
    ) {
        roomService.deleteComment(roomId, loginMemberId, commentId);
        return ResponseEntity.status(OK).build();
    }
}
