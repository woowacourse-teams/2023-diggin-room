package com.digginroom.digginroom.domain.member;

import com.digginroom.digginroom.domain.BaseEntity;
import com.digginroom.digginroom.domain.Genre;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGenre extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    private int weight;
    @ManyToOne
    private Member member;

    public MemberGenre(final Genre genre, final Member member) {
        this.genre = genre;
        this.weight = WeightStatus.DEFAULT.getValue();
        this.member = member;
    }

    public boolean isSameGenre(final Genre genre) {
        return this.genre == genre;
    }

    public void adjustWeight(final WeightFactor weightFactor) {
        this.weight = Integer.max(WeightStatus.LOWER_BOUND.getValue(), this.weight + weightFactor.getValue());
    }
}
