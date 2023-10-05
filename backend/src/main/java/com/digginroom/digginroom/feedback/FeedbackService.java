package com.digginroom.digginroom.feedback;

import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.feedback.domain.Feedback;
import com.digginroom.digginroom.feedback.dto.FeedbackRequest;
import com.digginroom.digginroom.feedback.dto.FeedbackResponse;
import com.digginroom.digginroom.feedback.repository.FeedbackRepository;
import com.digginroom.digginroom.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final MemberRepository memberRepository;

    public void accept(final Long memberId, final FeedbackRequest feedbackRequest) {
        Member member = memberRepository.getReferenceById(memberId);
        Feedback feedback = new Feedback(member, feedbackRequest.content());
        feedbackRepository.save(feedback);
    }

    public List<FeedbackResponse> getAll() {
        return feedbackRepository.findAll()
                .stream()
                .map(FeedbackResponse::of)
                .toList();
    }
}
