package com.digginroom.digginroom.service;

import com.digginroom.digginroom.controller.dto.CommentRequest;
import com.digginroom.digginroom.controller.dto.CommentResponse;
import com.digginroom.digginroom.domain.Comment;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.domain.Room;
import com.digginroom.digginroom.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponse comment(final Room room, final Member member, final CommentRequest request) {
        Comment comment = new Comment(room.getId(), request.comment(), member);
        commentRepository.save(comment);

        return CommentResponse.of(comment);
    }
}
