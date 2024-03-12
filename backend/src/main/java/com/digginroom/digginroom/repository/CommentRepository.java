package com.digginroom.digginroom.repository;

import com.digginroom.digginroom.domain.comment.Comment;
import com.digginroom.digginroom.repository.dto.CommentMember;
import com.digginroom.digginroom.exception.CommentException.NotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findCommentById(final Long id);

    default Comment getCommentById(final Long id) {
        return findCommentById(id).orElseThrow(NotFoundException::new);
    }

    @Query("SELECT new com.digginroom.digginroom.repository.dto.CommentMember("
            + "c.id, c.comment, c.createdAt, c.updatedAt, m.id, m.nickname.nickname"
            + ") "
            + "FROM Comment c JOIN Member m "
            + "ON c.memberId = m.id "
            + "WHERE c.roomId = :roomId "
            + "AND c.id < :lastCommentId "
            + "ORDER BY c.id DESC ")
    Slice<CommentMember> getCommentsByCursor(
            @Param("roomId") final Long roomId,
            @Param("lastCommentId") final Long lastCommentId,
            final Pageable pageable
    );
}
