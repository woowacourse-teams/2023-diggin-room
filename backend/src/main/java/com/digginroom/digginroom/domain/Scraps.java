package com.digginroom.digginroom.domain;

import com.digginroom.digginroom.exception.RoomException.AlreadyScrappedException;
import com.digginroom.digginroom.exception.RoomException.NotScrappedException;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scraps {

    @ManyToMany
    private final List<Room> scrapRooms = new ArrayList<>();

    public void scrap(final Room room) {
        validateUnscrapped(room);
        scrapRooms.add(room);
    }

    private void validateUnscrapped(final Room room) {
        if (hasScrapped(room)) {
            throw new AlreadyScrappedException();
        }
    }

    public boolean hasScrapped(final Room room) {
        return scrapRooms.contains(room);
    }

    public void unscrap(final Room room) {
        validateScrapped(room);
        scrapRooms.remove(room);
    }

    private void validateScrapped(final Room room) {
        if (!hasScrapped(room)) {
            throw new NotScrappedException();
        }
    }
}
