package com.digginroom.digginroom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGenre {

    private static final int DEFAULT_WEIGHT = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private Genre genre;
    private int weight;
    @ManyToOne
    private Member member;

    public MemberGenre(final Genre genre, final Member member) {
        this.genre = genre;
        this.weight = DEFAULT_WEIGHT;
        this.member = member;
    }
}
