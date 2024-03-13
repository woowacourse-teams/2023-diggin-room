package com.digginroom.digginroom.comment.service;

import static com.digginroom.digginroom.exception.CommentException.InvalidCommentSizeException;
import static com.digginroom.digginroom.exception.CommentException.NotOwnerException;

import com.digginroom.digginroom.comment.domain.Comment;
import com.digginroom.digginroom.comment.repository.CommentRepository;
import com.digginroom.digginroom.comment.repository.dto.CommentMember;
import com.digginroom.digginroom.comment.service.dto.CommentRequest;
import com.digginroom.digginroom.comment.service.dto.CommentResponse;
import com.digginroom.digginroom.comment.service.dto.CommentsResponse;
import com.digginroom.digginroom.exception.CommentException.InvalidLastCommentIdException;
import com.digginroom.digginroom.exception.RoomException.NotFoundException;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.repository.RoomRepository;
import com.digginroom.digginroom.repository.dto.MemberNickname;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private static final int DEFAULT_PAGE_SIZE = 0;

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;

    public CommentsResponse getRoomComments(
            final Long roomId,
            final Long memberId,
            final Long lastCommentId,
            final int size
    ) {
        Long resolvedLastCommentId = getLastCommentId(lastCommentId);
        validateLastCommentId(resolvedLastCommentId);
        validateCommentSize(size);
        Slice<CommentMember> commentMembers = commentRepository.getCommentsByCursor(
                roomId,
                resolvedLastCommentId,
                PageRequest.of(DEFAULT_PAGE_SIZE, size)
        );
        return new CommentsResponse(commentMembers.getContent().stream()
                .map(commentMember -> CommentResponse.of(commentMember, memberId))
                .toList());
    }

    private void validateLastCommentId(final Long lastCommentId) {
        if (lastCommentId <= 0) {
            throw new InvalidLastCommentIdException();
        }
    }

    private void validateCommentSize(final int size) {
        if (size <= 0) {
            throw new InvalidCommentSizeException();
        }
    }

    private Long getLastCommentId(final Long lastCommentId) {
        if (Objects.isNull(lastCommentId)) {
            return Long.MAX_VALUE;
        }
        return lastCommentId;
    }

    public CommentResponse comment(final Long roomId, final Long memberId, final CommentRequest request) {
        validateExistRoom(roomId);
        MemberNickname memberNickname = memberRepository.getMemberNickname(memberId);

        Comment comment = new Comment(roomId, request.comment(), memberId);
        commentRepository.save(comment);

        return CommentResponse.of(comment, comment.isOwner(memberId), memberNickname.getNickname());
    }

    public void validateExistRoom(final Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new NotFoundException(roomId);
        }
    }

    public void delete(final Long memberId, final Long commentId) {
        Comment comment = commentRepository.getCommentById(commentId);

        validateSameOwner(memberId, comment);
        commentRepository.delete(comment);
    }

    private void validateSameOwner(final Long memberId, final Comment comment) {
        if (!comment.isOwner(memberId)) {
            throw new NotOwnerException();
        }
    }

    public CommentResponse update(
            final Long memberId,
            final Long commentId,
            final CommentRequest request
    ) {
        MemberNickname memberNickname = memberRepository.getMemberNickname(memberId);
        Comment comment = commentRepository.getCommentById(commentId);
        comment.updateComment(request.comment(), memberId);
        return CommentResponse.of(comment, comment.isOwner(memberId), memberNickname.getNickname());
    }
}
