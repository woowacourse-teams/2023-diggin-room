package com.digginroom.digginroom.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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

    public Comment updateComment(final String comment) {
        this.comment = comment;
        return this;
    }

    public boolean isOwner(final Member member) {
        return this.member.equals(member);
    }

    public boolean isSameRoom(final Long roomId) {
        return this.roomId.equals(roomId);
    }
}
