package com.digginroom.digginroom.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    private Long roomId;
    @Lob
    @Column(length = 500)
    private String comment;
    @ManyToOne
    private Member member;

    public Comment(final Long roomId, final String comment, final Member member) {
        this.roomId = roomId;
        this.comment = comment;
        this.member = member;
    }

    public Long getElapsedTime() {
        LocalDateTime now = LocalDateTime.now();

        return Duration.between(createdAt, now).getSeconds();
    }

    public void updateComment(final String comment) {
        this.comment = comment;
    }

    public boolean isUpdated() {
        return this.createdAt.isEqual(updatedAt);
    }

    public boolean isOwner(final Member member) {
        return this.member.equals(member);
    }
}
