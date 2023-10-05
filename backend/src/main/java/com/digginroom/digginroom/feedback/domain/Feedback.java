package com.digginroom.digginroom.feedback.domain;

import com.digginroom.digginroom.domain.BaseEntity;
import com.digginroom.digginroom.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feedback extends BaseEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_feedback_writer"))
    private Member writer;
    private String content;

    public Feedback(final Member writer, final String content) {
        this.writer = writer;
        this.content = content;
    }
}
