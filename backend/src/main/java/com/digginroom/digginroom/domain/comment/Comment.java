package com.digginroom.digginroom.domain.comment;

import com.digginroom.digginroom.domain.BaseEntity;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.exception.CommentException.NotOwnerException;
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

    public void updateComment(final String comment, final Member member) {
        if (!isOwner(member)) {
            throw new NotOwnerException();
        }
        this.comment = comment;
    }

    public boolean isOwner(final Member member) {
        return this.member.equals(member);
    }
}
