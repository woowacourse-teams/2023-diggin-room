package com.digginroom.digginroom.domain;

import com.digginroom.digginroom.exception.RoomException.NotDislikedException;
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
public class DislikeRooms {

    @ManyToMany
    private final List<Room> dislikeRooms = new ArrayList<>();

    public void dislike(final Room room) {
        dislikeRooms.add(room);
    }

    public boolean hasDisliked(final Room room) {
        return dislikeRooms.contains(room);
    }

    public void undislike(final Room room) {
        validateDisliked(room);
        dislikeRooms.remove(room);
    }

    private void validateDisliked(final Room room) {
        if (!hasDisliked(room)) {
            throw new NotDislikedException();
        }
    }
}
