package com.digginroom.digginroom.domain;

import com.digginroom.digginroom.util.DigginRoomPasswordEncoder;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @Embedded
    private final ScrapRooms scrapRooms = new ScrapRooms();
    @Embedded
    private final DislikeRooms dislikeRooms = new DislikeRooms();
    @Embedded
    private final MemberGenres memberGenres = new MemberGenres(this);

    public Member(final String username, final String password) {
        this.username = username;
        this.password = DigginRoomPasswordEncoder.encode(password);
    }

    public boolean hasDifferentPassword(final String password) {
        return !DigginRoomPasswordEncoder.matches(password, this.password);
    }

    public void scrap(final Room room) {
        scrapRooms.scrap(room);
        adjustMemberGenreWeight(room, Weight.SCRAP);
    }

    private void adjustMemberGenreWeight(final Room room, final Weight scrap) {
        Genre superGenre = room.getTrack().getSuperGenre();
        memberGenres.adjustWeightBy(superGenre, scrap);
    }

    public void unscrap(final Room room) {
        scrapRooms.unscrap(room);
        adjustMemberGenreWeight(room, Weight.UNSCRAP);
    }

    public void dislike(final Room room) {
        dislikeRooms.dislike(room);
        adjustMemberGenreWeight(room, Weight.DISLIKE);
    }

    public void undislike(final Room room) {
        dislikeRooms.undislike(room);
        adjustMemberGenreWeight(room, Weight.UNDISLIKE);
    }

    public boolean hasScrapped(final Room pickedRoom) {
        return scrapRooms.hasScrapped(pickedRoom);
    }

    public List<MemberGenre> getMemberGenres() {
        return memberGenres.getMemberGenres();
    }

    public List<Room> getScrapRooms() {
        return scrapRooms.getScrapRooms();
    }

    public List<Room> getDislikeRooms() {
        return dislikeRooms.getDislikeRooms();
    }
}
