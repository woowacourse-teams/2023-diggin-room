package com.digginroom.digginroom.feedback;

import com.digginroom.digginroom.feedback.domain.Feedback;
import com.digginroom.digginroom.feedback.dto.FeedbackRequest;
import com.digginroom.digginroom.feedback.dto.FeedbackResponse;
import com.digginroom.digginroom.feedback.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public void accept(final FeedbackRequest feedbackRequest) {
        Feedback feedback = new Feedback(feedbackRequest.content());
        feedbackRepository.save(feedback);
    }

    public List<FeedbackResponse> getAll() {
        return feedbackRepository.findAll()
                .stream()
                .map(FeedbackResponse::of)
                .toList();
    }
}
