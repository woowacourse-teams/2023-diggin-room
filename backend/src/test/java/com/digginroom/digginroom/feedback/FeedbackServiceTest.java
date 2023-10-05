package com.digginroom.digginroom.feedback;

import com.digginroom.digginroom.feedback.domain.Feedback;
import com.digginroom.digginroom.feedback.dto.FeedbackRequest;
import com.digginroom.digginroom.feedback.dto.FeedbackResponse;
import com.digginroom.digginroom.feedback.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FeedbackServiceTest {

    @Autowired
    private FeedbackRepository feedbackRepository;
    private FeedbackService feedbackService;

    @BeforeEach
    void setUp() {
        this.feedbackService = new FeedbackService(feedbackRepository);
    }

    @Test
    void 피드백을_받는다() {
        String content = "피드백 창구를 만들어주세요";
        feedbackService.accept(new FeedbackRequest(content));

        assertThat(feedbackRepository.findAll())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactly(new Feedback(content));
    }

    @Test
    void 피드백을_전체조회한다() {
        String content = "피드백 창구를 빨랑 만들어주세요";
        feedbackRepository.save(new Feedback(content));

        assertThat(feedbackService.getAll()).containsExactly(new FeedbackResponse(content));
    }
}
