package com.digginroom.digginroom.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private MediaSource mediaSource;

    public Room(final MediaSource mediaSource) {
        validateNotNull(mediaSource);
        this.mediaSource = mediaSource;
    }

    private void validateNotNull(final MediaSource mediaSource) {
        if (Objects.isNull(mediaSource)) {
            throw new IllegalArgumentException("미디어 소스가 있어야 합니다");
        }
    }
}
