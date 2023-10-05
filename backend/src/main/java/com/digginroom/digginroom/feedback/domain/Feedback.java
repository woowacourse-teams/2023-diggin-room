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

    private String content;

    public Feedback(String content) {
        this.content = content;
    }
}
