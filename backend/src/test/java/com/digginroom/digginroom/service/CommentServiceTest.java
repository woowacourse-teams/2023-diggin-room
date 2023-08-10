package com.digginroom.digginroom.service;

import com.digginroom.digginroom.repository.CommentRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class CommentServiceTest {

    @Mock
    private MemberService memberService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ElapsedTimeConverter elapsedTimeConverter;
    @InjectMocks
    private CommentService commentService;

    @Test
    void 유저는_특정_룸에_댓글을_달_수_있다() {

    }

    @Test
    void 유저는_자신의_댓글을_수정할_수_있다() {

    }

    @Test
    void 유저는_자신의_댓글이_아니면_수정할_수_없다() {

    }

    @Test
    void 유저는_없는_룸에_댓글을_작성_시_예외가_발생한다() {

    }

    @Test
    void 유저는_500자_이상의_댓글을_달_경우_예외가_발생한다() {

    }

    @Test
    void 유저는_자신의_댓글을_삭제할_수_있다() {

    }

    @Test
    void 유저는_다른유저의_댓글을_삭제할_수_없다() {

    }

    @Test
    void 유저는_존재하지_않은_댓글을_삭제할_수_없다() {

    }

    @Test
    void 유저는_룸의_댓글을_전체_조회할_수_있다() {

    }

}
