package com.digginroom.digginroom.feedback.service;

import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.feedback.FeedbackService;
import com.digginroom.digginroom.feedback.domain.Feedback;
import com.digginroom.digginroom.feedback.dto.FeedbackRequest;
import com.digginroom.digginroom.feedback.dto.FeedbackResponse;
import com.digginroom.digginroom.feedback.repository.FeedbackRepository;
import com.digginroom.digginroom.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.digginroom.digginroom.TestFixture.블랙캣;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FeedbackServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    private FeedbackService feedbackService;

    private Member writer;

    @BeforeEach
    void setUp() {
        this.feedbackService = new FeedbackService(feedbackRepository);
        this.writer = memberRepository.save(블랙캣());
    }

    @Test
    void 피드백을_받는다() {
        String content = "피드백 창구를 만들어주세요";

        feedbackService.accept(writer.getId(), new FeedbackRequest(content));

        assertThat(feedbackRepository.findAll())
                .usingRecursiveFieldByFieldElementComparatorOnFields("content")
                .containsExactly(new Feedback(writer.getId(), content));
    }

    @Test
    void 피드백을_전체조회한다() {
        Feedback feedback = new Feedback(writer.getId(), "피드백 창구를 빨랑 만들어주세요");
        feedbackRepository.save(feedback);

        assertThat(feedbackService.getAll()).containsExactly(FeedbackResponse.of(feedback));
    }
}
