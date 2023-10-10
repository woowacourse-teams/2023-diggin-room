package com.digginroom.digginroom.feedback.repository;

import com.digginroom.digginroom.feedback.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
