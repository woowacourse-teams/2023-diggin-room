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
    private Long memberId;

    public void updateComment(final String comment, final Long memberId) {
        if (!isOwner(memberId)) {
            throw new NotOwnerException();
        }
        this.comment = comment;
    }

    public boolean isOwner(final Long memberId) {
        return this.memberId.equals(memberId);
    }
}
