package com.digginroom.digginroom.service;

import static com.digginroom.digginroom.exception.CommentException.InvalidCommentSizeException;
import static com.digginroom.digginroom.exception.CommentException.NotOwnerException;

import com.digginroom.digginroom.domain.comment.Comment;
import com.digginroom.digginroom.exception.CommentException.InvalidLastCommentIdException;
import com.digginroom.digginroom.exception.RoomException.NotFoundException;
import com.digginroom.digginroom.repository.CommentRepository;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.repository.RoomRepository;
import com.digginroom.digginroom.repository.dto.CommentInfo;
import com.digginroom.digginroom.repository.dto.CommentMember;
import com.digginroom.digginroom.repository.dto.CommentMemberId;
import com.digginroom.digginroom.repository.dto.MemberNickname;
import com.digginroom.digginroom.service.dto.CommentRequest;
import com.digginroom.digginroom.service.dto.CommentResponse;
import com.digginroom.digginroom.service.dto.CommentsResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
    private final ExecutorService commentThreadPool = Executors.newCachedThreadPool();

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

    public CommentsResponse getRoomComments2(
            final Long roomId,
            final Long memberId,
            final Long lastCommentId,
            final int size,
            final int flag
    ) {
        Long resolvedLastCommentId = getLastCommentId(lastCommentId);
        validateLastCommentId(resolvedLastCommentId);
        validateCommentSize(size);

        if (flag == 1) {
            Slice<CommentMember> commentMembers = commentRepository.getCommentsByCursor(
                    roomId,
                    resolvedLastCommentId,
                    PageRequest.of(DEFAULT_PAGE_SIZE, size)
            );
            return new CommentsResponse(commentMembers.getContent().stream()
                    .map(commentMember -> CommentResponse.of(commentMember, memberId))
                    .toList());
        }
        if (flag == 2) {
            List<Long> commentsId = commentRepository.getCommentsId(roomId,
                    resolvedLastCommentId,
                    PageRequest.of(DEFAULT_PAGE_SIZE, size)
            );
            Slice<CommentMember> commentMembers = commentRepository.getCommentsByCursor2(commentsId);
            return new CommentsResponse(commentMembers.getContent().stream()
                    .map(commentMember -> CommentResponse.of(commentMember, memberId))
                    .toList());
        }
        if (flag == 3) {
            Slice<CommentMember> commentMembers = commentRepository.getCommentsByCursor3(roomId,
                    resolvedLastCommentId,
                    PageRequest.of(DEFAULT_PAGE_SIZE, size)
            );
            return new CommentsResponse(commentMembers.getContent().stream()
                    .map(commentMember -> CommentResponse.of(commentMember, memberId))
                    .toList());
        }

        List<CommentMemberId> commentsId = commentRepository.getCommentsId2(roomId,
                resolvedLastCommentId,
                PageRequest.of(DEFAULT_PAGE_SIZE, size)
        );

        Future<Map<Long, MemberNickname>> blockedMemberIdToNickname = commentThreadPool.submit(
                () -> memberRepository.getMemberNicknameByIdIn(commentsId.stream()
                        .map(CommentMemberId::memberId)
                        .toList()));
        Future<Slice<CommentInfo>> blockedCommentInfo = commentThreadPool.submit(
                () -> commentRepository.getCommentsByCursor4(commentsId.stream()
                        .map(CommentMemberId::id)
                        .toList()));

        List<CommentResponse> comments = new ArrayList<>();
        try {
            Map<Long, MemberNickname> memberIdToMemberNicknames = blockedMemberIdToNickname.get();
            blockedCommentInfo.get().stream()
                    .forEach(commentInfo -> comments.add(CommentResponse.of(
                            commentInfo, memberIdToMemberNicknames.get(commentInfo.memberId()), memberId
                            )));
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalArgumentException(e);
        }
        return new CommentsResponse(comments);
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

        return CommentResponse.of(comment, comment.isOwner(memberId), memberNickname.nickName());
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
        return CommentResponse.of(comment, comment.isOwner(memberId), memberNickname.nickName());
    }
}
