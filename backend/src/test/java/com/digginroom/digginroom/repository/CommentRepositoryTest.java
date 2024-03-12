package com.digginroom.digginroom.repository;

import static com.digginroom.digginroom.TestFixture.블랙캣;
import static com.digginroom.digginroom.TestFixture.파워;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.digginroom.digginroom.comment.domain.Comment;
import com.digginroom.digginroom.comment.repository.CommentRepository;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.exception.CommentException;
import com.digginroom.digginroom.comment.repository.dto.CommentMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.LongStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CommentRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    void init() {
        final Member 파워 = 멤버를_저장_후_가져_온다(파워());
        final Member 블랙캣 = 멤버를_저장_후_가져_온다(블랙캣());

        멤버가_룸에_댓글을_여러_개를_저장_한다(1L, 파워, 5);
        멤버가_룸에_댓글을_여러_개를_저장_한다(1L, 블랙캣, 5);
        멤버가_룸에_댓글을_여러_개를_저장_한다(1L, 파워, 5);
        멤버가_룸에_댓글을_여러_개를_저장_한다(1L, 블랙캣, 5);

        em.flush();
        em.clear();
    }

    private Member 멤버를_저장_후_가져_온다(final Member 멤버) {
        em.persist(멤버);
        return 멤버;
    }

    private void 멤버가_룸에_댓글을_여러_개를_저장_한다(final long 룸ID, final Member 멤버, final int 저장개수) {
        for (int index = 0; index < 저장개수; index++) {
            Comment comment = new Comment(룸ID, "댓글입니다.", 멤버.getId());
            em.persist(comment);
        }
    }

    @Test
    void 댓글_정보가_없는_경우_에러가_발생한다() {
        assertThatThrownBy(() -> commentRepository.getCommentById(Long.MAX_VALUE))
                .isInstanceOf(CommentException.NotFoundException.class);
    }

    @Test
    void 마지막으로_본_댓글이_주어지고_size_가_10인_경우_해당_룸의_댓글_ID_부터_최신_댓글_부터_10개를_반환한다() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Slice<CommentMember> comments = commentRepository.getCommentsByCursor(1L, 15L, pageRequest);

        assertThat(comments.getContent())
                .hasSize(10)
                .map(CommentMember::id)
                .usingRecursiveComparison()
                .isEqualTo(getReversedList(5, 14));
    }

    private List<Long> getReversedList(final int startIndex, final int endIndex) {
        return LongStream.rangeClosed(startIndex, endIndex)
                .boxed()
                .sorted((i1, i2) -> Math.toIntExact(i2 - i1))
                .toList();
    }

    @Test
    void 마지막으로_본_댓글이_주어지고_size_가_2인_경우_해당_룸의_댓글_ID_부터_최신_댓글_부터_2개를_반환한다() {
        PageRequest pageRequest = PageRequest.of(0, 2);

        Slice<CommentMember> comments = commentRepository.getCommentsByCursor(1L, 15L, pageRequest);

        assertThat(comments.getContent())
                .hasSize(2)
                .map(CommentMember::id)
                .usingRecursiveComparison()
                .isEqualTo(getReversedList(13, 14));
    }

    @Test
    void 마지막으로_본_댓글이_주어졌을_때_size_가_10이고_남은_댓글이_10개_이하인_경우_남은_댓글만_반환한다() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Slice<CommentMember> comments = commentRepository.getCommentsByCursor(1L, 5L, pageRequest);

        assertThat(comments.getContent())
                .hasSize(4)
                .map(CommentMember::id)
                .usingRecursiveComparison()
                .isEqualTo(getReversedList(1, 4));
    }

    @Test
    void 마지막으로_본_댓글이_주어지고_룸에_그_이하의_댓글이_없는_경우_빈_결과를_반환한다() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Slice<CommentMember> comments = commentRepository.getCommentsByCursor(2L, 10L, pageRequest);

        assertThat(comments.getContent()).isEmpty();
    }
}
