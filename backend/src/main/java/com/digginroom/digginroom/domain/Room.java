package com.digginroom.digginroom.domain;

import static com.digginroom.digginroom.exception.RoomException.NoMediaSourceException;

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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private MediaSource mediaSource;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Track track;

    public Room(final MediaSource mediaSource, final Track track) {
        validateMediaSourceNotNull(mediaSource);
        this.mediaSource = mediaSource;
        this.track = track;
    }

    private void validateMediaSourceNotNull(final MediaSource mediaSource) {
        if (Objects.isNull(mediaSource)) {
            throw new NoMediaSourceException();
        }
    }
}
