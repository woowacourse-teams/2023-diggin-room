package com.digginroom.digginroom.repository;

import com.digginroom.digginroom.domain.comment.Comment;
import com.digginroom.digginroom.repository.dto.CommentMember;
import com.digginroom.digginroom.exception.CommentException.NotFoundException;
import com.digginroom.digginroom.repository.dto.CommentInfo;
import com.digginroom.digginroom.repository.dto.CommentMemberId;
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

    @Query("select c.id "
            + "from Comment c "
            + "where c.roomId = :roomId "
            + "and c.id < :lastCommentId "
            + "order by c.id desc ")
    List<Long> getCommentsId(
            @Param("roomId") final Long roomId,
            @Param("lastCommentId") final Long lastCommentId,
            final Pageable pageable);


    @Query("SELECT new com.digginroom.digginroom.repository.dto.CommentMember("
            + "c.id, c.comment, c.createdAt, c.updatedAt, m.id, m.nickname.nickname"
            + ") "
            + "FROM Comment c JOIN Member m "
            + "ON c.memberId = m.id "
            + "where c.id in :ids "
            + "ORDER BY c.id DESC ")
    Slice<CommentMember> getCommentsByCursor2(@Param("ids") final List<Long> ids);

    @Query("SELECT new com.digginroom.digginroom.repository.dto.CommentMember("
            + "c.id, c.comment, c.createdAt, c.updatedAt, m.id, m.nickname.nickname"
            + ") "
            + "FROM Comment as c JOIN Member as m "
            + "ON c.memberId = m.id "
            + "JOIN (SELECT c2.id as sub_id"
            + "     FROM Comment as c2 "
            + "     WHERE c2.roomId = :roomId and c2.id < :lastCommentId) as sub "
            + "ON c.id = sub.sub_id "
            + "ORDER BY sub.sub_id DESC ")
    Slice<CommentMember> getCommentsByCursor3(
            @Param("roomId") final Long roomId,
            @Param("lastCommentId") final Long lastCommentId,
            final Pageable pageable
    );

    @Query("select new com.digginroom.digginroom.repository.dto.CommentMemberId(c.id, c.memberId) "
            + "from Comment c "
            + "where c.roomId = :roomId "
            + "and c.id < :lastCommentId "
            + "order by c.id desc ")
    List<CommentMemberId> getCommentsId2(
            @Param("roomId") final Long roomId,
            @Param("lastCommentId") final Long lastCommentId,
            final Pageable pageable);

    @Query("SELECT new com.digginroom.digginroom.repository.dto.CommentInfo(c.id, c.comment, c.createdAt, c.updatedAt, c.memberId) "
            + "FROM Comment c "
            + "where c.id in :ids "
            + "ORDER BY c.id DESC ")
    Slice<CommentInfo> getCommentsByCursor4(@Param("ids") final List<Long> ids);

}
