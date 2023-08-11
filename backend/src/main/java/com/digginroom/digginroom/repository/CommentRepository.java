package com.digginroom.digginroom.repository;

import com.digginroom.digginroom.domain.Comment;
import com.digginroom.digginroom.exception.CommentException.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> getCommentByRoomIdAndId(final Long roomId, final Long commentId);

    default Comment findCommentByRoomIdAndId(final Long roomId, final Long commentId) {
        return getCommentByRoomIdAndId(roomId, commentId)
                .orElseThrow(NotFoundException::new);
    }

    List<Comment> findCommentByRoomId(Long roomId);
}
