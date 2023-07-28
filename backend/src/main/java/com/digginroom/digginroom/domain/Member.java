package com.digginroom.digginroom.domain;

import com.digginroom.digginroom.exception.RoomException.AlreadyDislikeException;
import com.digginroom.digginroom.exception.RoomException.AlreadyScrappedException;
import com.digginroom.digginroom.exception.RoomException.NotScrappedException;
import com.digginroom.digginroom.util.DigginRoomPasswordEncoder;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @ManyToMany
    private final List<Room> scrapRooms = new ArrayList<>();
    @ManyToMany
    private final List<Room> dislikeRooms = new ArrayList<>();

    public Member(@NonNull final String username, @NonNull final String password) {
        this.username = username;
        this.password = DigginRoomPasswordEncoder.encode(password);
    }

    public boolean hasDifferentPassword(final String password) {
        return !DigginRoomPasswordEncoder.matches(password, this.password);
    }

    public void scrap(final Room room) {
        validateUnscrapped(room);
        validateUnDisliked(room);
        scrapRooms.add(room);
    }

    public void unscrap(final Room room) {
        validateScrapped(room);
        scrapRooms.remove(room);
    }

    public boolean hasScrapped(final Room room) {
        return scrapRooms.contains(room);
    }

    private void validateUnscrapped(final Room room) {
        if (hasScrapped(room)) {
            throw new AlreadyScrappedException();
        }
    }

    private void validateScrapped(final Room room) {
        if (!hasScrapped(room)) {
            throw new NotScrappedException();
        }
    }

    public void dislike(final Room room) {
        validateUnscrapped(room);
        validateUnDisliked(room);
        dislikeRooms.add(room);
    }

    private void validateUnDisliked(final Room room) {
        if (hasDislike(room)) {
            throw new AlreadyDislikeException();
        }
    }

    private boolean hasDislike(final Room room) {
        return dislikeRooms.contains(room);
    }
}
