package com.digginroom.digginroom.domain.room;

import static com.digginroom.digginroom.exception.RoomException.NoMediaSourceException;

import com.digginroom.digginroom.domain.BaseEntity;
import com.digginroom.digginroom.domain.mediasource.MediaSource;
import com.digginroom.digginroom.domain.track.Track;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseEntity {

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST)
    private MediaSource mediaSource;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Track track;
    @Column(nullable = false)
    @ColumnDefault(value = "0")
    private Long scrapCount;

    public Room(final MediaSource mediaSource, final Track track) {
        validateNotNull(mediaSource);
        this.mediaSource = mediaSource;
        this.track = track;
        this.scrapCount = 0L;
    }

    private void validateNotNull(final MediaSource mediaSource) {
        if (Objects.isNull(mediaSource)) {
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
