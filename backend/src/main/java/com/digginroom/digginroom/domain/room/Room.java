package com.digginroom.digginroom.domain.room;

import com.digginroom.digginroom.domain.BaseEntity;
import com.digginroom.digginroom.domain.track.Track;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;

import static com.digginroom.digginroom.exception.RoomException.NoMediaSourceException;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseEntity {

    @NotNull
    private String identifier;
    @Embedded
    private Track track;
    @Column(nullable = false)
    @ColumnDefault(value = "0")
    private Long scrapCount;

    public Room(final String identifier, final Track track) {
        validateNotNull(identifier);
        this.identifier = identifier;
        this.track = track;
        this.scrapCount = 0L;
    }

    private void validateNotNull(final String identifier) {
        if (Objects.isNull(identifier)) {
            throw new NoMediaSourceException();
        }
    }

    public void increaseScrapCount() {
        scrapCount++;
    }

    public void decreaseScrapCount() {
        scrapCount--;
    }
}
