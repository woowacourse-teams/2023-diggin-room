package com.digginroom.digginroom.repository;

import com.digginroom.digginroom.domain.Comment;
import com.digginroom.digginroom.exception.CommentException.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentById(final Long id);

    default Comment getCommentById(final Long id) {
        return findCommentById(id).orElseThrow(NotFoundException::new);
    }

    List<Comment> findCommentsByRoomId(Long roomId);
}
