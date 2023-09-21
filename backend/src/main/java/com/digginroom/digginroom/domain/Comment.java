package com.digginroom.digginroom.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    private Long roomId;
    @Column(length = 500)
    private String comment;
    @ManyToOne
    private Member member;

    public void updateComment(final String comment) {
        this.comment = comment;
    }

    public boolean isOwner(final Member member) {
        return this.member.equals(member);
    }

    public boolean isSameRoom(final Long roomId) {
        return this.roomId.equals(roomId);
    }
}