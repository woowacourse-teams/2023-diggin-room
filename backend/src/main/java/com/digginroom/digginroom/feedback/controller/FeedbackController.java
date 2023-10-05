package com.digginroom.digginroom.feedback.controller;

import com.digginroom.digginroom.controller.Auth;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.feedback.FeedbackService;
import com.digginroom.digginroom.feedback.dto.FeedbackRequest;
import com.digginroom.digginroom.feedback.dto.FeedbackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping("/feedbacks")
    public ResponseEntity<Void> takeFeedbackFrom(@Auth Member member, final @RequestBody FeedbackRequest feedback) {
        feedbackService.accept(feedback);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<List<FeedbackResponse>> showFeedbacks() {
        return ResponseEntity.ok().body(feedbackService.getAll());
    }
}
