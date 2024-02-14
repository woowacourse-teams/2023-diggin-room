package com.digginroom.digginroom.membergenre.domain;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.UUIDBaseEntity;
import com.digginroom.digginroom.membergenre.domain.vo.WeightFactor;
import com.digginroom.digginroom.membergenre.domain.vo.WeightStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGenre extends UUIDBaseEntity {

    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    private Long memberId;
    private int weight;

    public MemberGenre(final Genre genre, final Long memberId) {
        this.genre = genre;
        this.weight = WeightStatus.DEFAULT.getValue();
        this.memberId = memberId;
    }

    public boolean isSameGenre(final Genre genre) {
        return this.genre == genre;
    }

    public void adjustWeight(final WeightFactor weightFactor) {
        this.weight = Integer.max(WeightStatus.LOWER_BOUND.getValue(), this.weight + weightFactor.getValue());
    }
}
