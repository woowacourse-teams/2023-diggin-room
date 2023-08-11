package com.digginroom.digginroom.service;

import static com.digginroom.digginroom.exception.CommentException.NotOwnerException;

import com.digginroom.digginroom.controller.dto.CommentRequest;
import com.digginroom.digginroom.controller.dto.CommentResponse;
import com.digginroom.digginroom.controller.dto.CommentsResponse;
import com.digginroom.digginroom.domain.Comment;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.domain.Room;
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

    public CommentResponse comment(final Room room, final Member member, final CommentRequest request) {
        Comment comment = new Comment(room.getId(), request.comment(), member);
        commentRepository.save(comment);

        return CommentResponse.of(comment);
    }

    public void delete(final Room room, final Member member, final Long commentId) {
        Comment comment = commentRepository.findCommentByRoomIdAndId(room.getId(), commentId);

        isOwner(comment, member);

        commentRepository.delete(comment);
    }

    private void isOwner(final Comment comment, final Member member) {
        if (!comment.isOwner(member)) {
            throw new NotOwnerException();
        }
    }
}
