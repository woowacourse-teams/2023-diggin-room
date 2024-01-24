package com.digginroom.digginroom.feedback.controller;

import com.digginroom.digginroom.controller.Auth;
import com.digginroom.digginroom.feedback.FeedbackService;
import com.digginroom.digginroom.feedback.dto.FeedbackRequest;
import com.digginroom.digginroom.feedback.dto.FeedbackResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Void> takeFeedbackFrom(
            @Auth final Long memberId,
            @Valid @RequestBody final FeedbackRequest feedback
    ) {
        feedbackService.accept(memberId, feedback);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> showFeedbacks() {
        return ResponseEntity.ok().body(feedbackService.getAll());
    }
}
