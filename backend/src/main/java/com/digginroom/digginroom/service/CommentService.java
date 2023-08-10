package com.digginroom.digginroom.service;

import com.digginroom.digginroom.controller.dto.CommentRequest;
import com.digginroom.digginroom.controller.dto.CommentResponse;
import com.digginroom.digginroom.domain.Comment;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final ElapsedTimeConverter elapsedTimeConverter;

    public CommentResponse comment(final Long roomId, final Long memberId, final CommentRequest request) {
        Member member = memberService.findMember(memberId);
        Comment comment = new Comment(roomId, request.comment(), member);
        commentRepository.save(comment);
        return new CommentResponse(
                comment.getMember().getUsername(),
                comment.getComment(),
                elapsedTimeConverter.convert(comment.getElapsedTime())
        );
    }
}
