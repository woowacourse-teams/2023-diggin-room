package com.digginroom.digginroom.domain;

import com.digginroom.digginroom.exception.RoomException.AlreadyDislikeException;
import com.digginroom.digginroom.exception.RoomException.AlreadyScrappedException;
import com.digginroom.digginroom.util.DigginRoomPasswordEncoder;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @Embedded
    private final ScrapRooms scrapRooms = new ScrapRooms();
    @Embedded
    private final DislikeRooms dislikeRooms = new DislikeRooms();
    @Embedded
    private final MemberGenres memberGenres = new MemberGenres(this);

    public Member(@NonNull final String username, @NonNull final String password) {
        this.username = username;
        this.password = DigginRoomPasswordEncoder.encode(password);
    }

    public boolean hasDifferentPassword(final String password) {
        return !DigginRoomPasswordEncoder.matches(password, this.password);
    }

    public void scrap(final Room room) {
        validateNotDisliked(room);
        scrapRooms.scrap(room);
        Genre superGenre = room.getTrack().getSuperGenre();
        memberGenres.adjustWeightBy(superGenre, Weight.SCRAP);
    }

    private void validateNotDisliked(final Room room) {
        if (dislikeRooms.hasDisliked(room)) {
            throw new AlreadyDislikeException();
        }
    }

    public void unscrap(final Room room) {
        scrapRooms.unscrap(room);
        Genre superGenre = room.getTrack().getSuperGenre();
        memberGenres.adjustWeightBy(superGenre, Weight.UNSCRAP);
    }

    public void dislike(final Room room) {
        validateNotScrapped(room);
        dislikeRooms.dislike(room);
        Genre superGenre = room.getTrack().getSuperGenre();
        memberGenres.adjustWeightBy(superGenre, Weight.DISLIKE);
    }

    private void validateNotScrapped(final Room room) {
        if (scrapRooms.hasScrapped(room)) {
            throw new AlreadyScrappedException();
        }
    }

    public void undislike(final Room room) {
        dislikeRooms.undislike(room);
        Genre superGenre = room.getTrack().getSuperGenre();
        memberGenres.adjustWeightBy(superGenre, Weight.UNDISLIKE);
    }

    public boolean hasScrapped(final Room pickedRoom) {
        return scrapRooms.hasScrapped(pickedRoom);
    }

    public MemberGenres getMemberGenres() {
        return memberGenres;
    }
}
