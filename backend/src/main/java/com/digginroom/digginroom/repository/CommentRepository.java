package com.digginroom.digginroom.repository;

import com.digginroom.digginroom.domain.comment.Comment;
import com.digginroom.digginroom.exception.CommentException.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentById(final Long id);

    default Comment getCommentById(final Long id) {
        return findCommentById(id).orElseThrow(NotFoundException::new);
    }

    List<Comment> findCommentsByRoomId(Long roomId);

    @Query("SELECT c "
            + "FROM Comment c "
            + "JOIN FETCH c.member "
            + "WHERE c.roomId = :roomId "
            + "AND c.id < :lastCommentId "
            + "ORDER BY c.id DESC "
    )
    Page<Comment> getCommentsByCursor(
            @Param("roomId") final Long roomId,
            @Param("lastCommentId") final Long lastCommentId,
            final Pageable pageable
    );

}
