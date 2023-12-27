package com.digginroom.digginroom.feedback.domain;

import com.digginroom.digginroom.domain.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback extends BaseEntity {

    private Long writerId;
    private String content;

    public Feedback(final Long writerId, final String content) {
        this.writerId = writerId;
        this.content = content;
    }
}
