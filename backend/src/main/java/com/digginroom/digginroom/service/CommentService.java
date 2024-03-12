package com.digginroom.digginroom.service;

import static com.digginroom.digginroom.exception.CommentException.InvalidCommentSizeException;
import static com.digginroom.digginroom.exception.CommentException.NotOwnerException;

import com.digginroom.digginroom.domain.comment.Comment;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.exception.CommentException.InvalidLastCommentIdException;
import com.digginroom.digginroom.exception.RoomException.NotFoundException;
import com.digginroom.digginroom.repository.CommentRepository;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.repository.RoomRepository;
import com.digginroom.digginroom.service.dto.CommentRequest;
import com.digginroom.digginroom.service.dto.CommentResponse;
import com.digginroom.digginroom.service.dto.CommentsResponse;
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

        Member member = memberRepository.getMemberById(memberId);
        Slice<Comment> comments = commentRepository.getCommentsByCursor(
                roomId,
                resolvedLastCommentId,
                PageRequest.of(DEFAULT_PAGE_SIZE, size)
        );

        return new CommentsResponse(comments.getContent().stream()
                .map(comment -> CommentResponse.of(comment, comment.isOwner(member)))
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
        Member member = memberRepository.getMemberById(memberId);

        Comment comment = new Comment(roomId, request.comment(), member);
        commentRepository.save(comment);

        return CommentResponse.of(comment, comment.isOwner(member));
    }

    public void validateExistRoom(final Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new NotFoundException(roomId);
        }
    }

    public void delete(final Long memberId, final Long commentId) {
        Member member = memberRepository.getMemberById(memberId);
        Comment comment = commentRepository.getCommentById(commentId);

        validateSameOwner(member, comment);
        commentRepository.delete(comment);
    }

    private void validateSameOwner(final Member member, final Comment comment) {
        if (!comment.isOwner(member)) {
            throw new NotOwnerException();
        }
    }

    public CommentResponse update(
            final Long memberId,
            final Long commentId,
            final CommentRequest request
    ) {
        Member member = memberRepository.getMemberById(memberId);
        Comment comment = commentRepository.getCommentById(commentId);
        comment.updateComment(request.comment(), member);
        return CommentResponse.of(comment, comment.isOwner(member));
    }
}
