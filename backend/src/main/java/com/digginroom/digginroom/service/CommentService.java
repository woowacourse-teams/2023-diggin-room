package com.digginroom.digginroom.service;

import static com.digginroom.digginroom.exception.CommentException.NotOwnerException;

import com.digginroom.digginroom.controller.dto.CommentRequest;
import com.digginroom.digginroom.controller.dto.CommentResponse;
import com.digginroom.digginroom.controller.dto.CommentsResponse;
import com.digginroom.digginroom.domain.Comment;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.exception.CommentException.NotSameRoomException;
import com.digginroom.digginroom.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentsResponse findRoomComments(final Long roomId) {
        List<Comment> comments = commentRepository.findCommentByRoomId(roomId);
        return new CommentsResponse(comments.stream()
                .map(CommentResponse::of)
                .toList());
    }

    public CommentResponse comment(final Long roomId, final Member member, final CommentRequest request) {
        Comment comment = new Comment(roomId, request.comment(), member);
        commentRepository.save(comment);

        return CommentResponse.of(comment);
    }

    public void delete(final Long roomId, final Member member, final Long commentId) {
        Comment comment = commentRepository.getCommentById(commentId);

        validateSameRoomAndOwner(member, roomId, comment);
        commentRepository.delete(comment);
    }

    public Comment update(final Member member, final Long roomId, final Long commentId, final CommentRequest request) {
        Comment comment = commentRepository.getCommentById(commentId);
        validateSameRoomAndOwner(member, roomId, comment);
        return comment.updateComment(request.comment());
    }

    private void validateSameRoomAndOwner(final Member member, final Long roomId, final Comment comment) {
        validateSameRoom(roomId, comment);
        validateSameOwner(member, comment);
    }

    private void validateSameOwner(final Member member, final Comment comment) {
        if (!comment.isOwner(member)) {
            throw new NotOwnerException();
        }
    }

    private void validateSameRoom(final Long roomId, final Comment comment) {
        if (!comment.isSameRoom(roomId)) {
            throw new NotSameRoomException();
        }
    }
}
