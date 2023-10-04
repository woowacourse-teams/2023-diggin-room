package com.digginroom.digginroom.service;

import static com.digginroom.digginroom.exception.CommentException.NotOwnerException;

import com.digginroom.digginroom.domain.comment.Comment;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.exception.RoomException.NotFoundException;
import com.digginroom.digginroom.repository.CommentRepository;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.repository.RoomRepository;
import com.digginroom.digginroom.service.dto.CommentRequest;
import com.digginroom.digginroom.service.dto.CommentResponse;
import com.digginroom.digginroom.service.dto.CommentsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;

    public CommentsResponse getRoomComments(final Long roomId, final Long memberId) {
        Member member = memberRepository.getMemberById(memberId);
        List<Comment> comments = commentRepository.findCommentsByRoomId(roomId);
        return new CommentsResponse(comments.stream()
                .map(comment -> CommentResponse.of(comment, comment.isOwner(member)))
                .toList());
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
