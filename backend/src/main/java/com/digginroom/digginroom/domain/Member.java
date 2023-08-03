package com.digginroom.digginroom.domain;

import com.digginroom.digginroom.exception.MemberGenreException.NotFoundException;
import com.digginroom.digginroom.exception.RoomException.AlreadyDislikeException;
import com.digginroom.digginroom.exception.RoomException.AlreadyScrappedException;
import com.digginroom.digginroom.util.DigginRoomPasswordEncoder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Arrays;
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
    private final Scraps scraps = new Scraps();
    @Embedded
    private final Dislikes dislikes = new Dislikes();
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private final List<MemberGenre> memberGenres = Arrays.stream(Genre.values())
            .map(it -> new MemberGenre(it, this))
            .toList();

    public Member(@NonNull final String username, @NonNull final String password) {
        this.username = username;
        this.password = DigginRoomPasswordEncoder.encode(password);
    }

    public boolean hasDifferentPassword(final String password) {
        return !DigginRoomPasswordEncoder.matches(password, this.password);
    }

    public void scrap(final Room room) {
        validateNotDisliked(room);
        scraps.scrap(room);
        Genre superGenre = room.getTrack().getSuperGenre();
        MemberGenre targetMemberGenre = memberGenres.stream()
                .filter(it -> it.isSameGenre(superGenre))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(superGenre));
        targetMemberGenre.increaseWeight();
    }

    private void validateNotDisliked(final Room room) {
        if (dislikes.hasDisliked(room)) {
            throw new AlreadyDislikeException();
        }
    }

    public void unscrap(final Room room) {
        scraps.unscrap(room);
        Genre superGenre = room.getTrack().getSuperGenre();
        MemberGenre targetMemberGenre = memberGenres.stream()
                .filter(it -> it.isSameGenre(superGenre))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(superGenre));
        targetMemberGenre.decreaseWeight();
    }

    public void dislike(final Room room) {
        validateNotScrapped(room);
        dislikes.dislike(room);
    }

    private void validateNotScrapped(final Room room) {
        if (scraps.hasScrapped(room)) {
            throw new AlreadyScrappedException();
        }
    }

    public void undislike(final Room room) {
        dislikes.undislike(room);
    }

    public boolean hasScrapped(final Room pickedRoom) {
        return scraps.hasScrapped(pickedRoom);
    }

    public List<MemberGenre> getMemberGenres() {
        return memberGenres;
    }
}
