package com.digginroom.digginroom.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.digginroom.digginroom.comment.service.CommentService;
import com.digginroom.digginroom.exception.CommentException.InvalidCommentSizeException;
import com.digginroom.digginroom.exception.CommentException.InvalidLastCommentIdException;
import com.digginroom.digginroom.comment.repository.CommentRepository;
import com.digginroom.digginroom.comment.repository.dto.CommentMember;
import com.digginroom.digginroom.comment.service.dto.CommentResponse;
import com.digginroom.digginroom.comment.service.dto.CommentsResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentService commentService;

    @Test
    void 댓글을_무한_스크롤로_조회할_수_있다() {
        LocalDateTime now = LocalDateTime.now();
        CommentMember kong = new CommentMember(1L, "kong", now, now, 1L, "kong1");
        CommentMember blackCat = new CommentMember(1L, "blackCat", now, now, 1L, "blackCat");
        List<CommentMember> comments = List.of(blackCat, kong);

        when(commentRepository.getCommentsByCursor(1L, 10L, PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(comments));

        CommentsResponse roomComments = commentService.getRoomComments(1L, 1L, 10L, 10);

        assertThat(roomComments.comments())
                .hasSize(2)
                .map(CommentResponse::comment)
                .containsExactly("blackCat", "kong");
    }

    @Test
    void 마지막으로_본_댓글이_없는_경우_Long_MAX_VALUE로_검색한다() {
        LocalDateTime now = LocalDateTime.now();
        CommentMember kong = new CommentMember(1L, "kong", now, now, 1L, "kong1");
        CommentMember blackCat = new CommentMember(1L, "blackCat", now, now, 1L, "blackCat");
        List<CommentMember> comments = List.of(blackCat, kong);

        when(commentRepository.getCommentsByCursor(1L, Long.MAX_VALUE, PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(comments));

        CommentsResponse roomComments = commentService.getRoomComments(1L, 1L, null, 10);

        assertThat(roomComments.comments())
                .hasSize(2)
                .map(CommentResponse::comment)
                .containsExactly("blackCat", "kong");
    }

    @Test
    void 페이지의_사이즈가_1보다_작은_경우_예외가_발생한다() {
        assertThatThrownBy(() -> commentService.getRoomComments(1L, 1L, 1L, 0))
                .isInstanceOf(InvalidCommentSizeException.class);
    }

    @Test
    void 마지막으로_본_댓글의_식별자가_1보다_작은_경우_예외가_발생한다() {
        assertThatThrownBy(() -> commentService.getRoomComments(1L, 1L, 0L, 10))
                .isInstanceOf(InvalidLastCommentIdException.class);
    }

}
