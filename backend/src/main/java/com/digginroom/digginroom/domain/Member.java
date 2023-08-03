package com.digginroom.digginroom.domain;

import com.digginroom.digginroom.exception.RoomException.AlreadyDislikeException;
import com.digginroom.digginroom.exception.RoomException.AlreadyScrappedException;
import com.digginroom.digginroom.exception.RoomException.NotDislikedException;
import com.digginroom.digginroom.exception.RoomException.NotScrappedException;
import com.digginroom.digginroom.util.DigginRoomPasswordEncoder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
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
    @ManyToMany
    private final List<Room> scrapRooms = new ArrayList<>();
    @ManyToMany
    private final List<Room> dislikeRooms = new ArrayList<>();
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
        validateUnscrapped(room);
        validateUndisliked(room);
        scrapRooms.add(room);
    }

    private void validateUnscrapped(final Room room) {
        if (hasScrapped(room)) {
            throw new AlreadyScrappedException();
        }
    }

    private void validateUndisliked(final Room room) {
        if (hasDisliked(room)) {
            throw new AlreadyDislikeException();
        }
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

    public List<MemberGenre> getMemberGenres() {
        return memberGenres;
    }

    public boolean hasScrapped(final Room room) {
        return scrapRooms.contains(room);
    }

    public void dislike(final Room room) {
        validateUnscrapped(room);
        validateUndisliked(room);
        dislikeRooms.add(room);
    }

    private boolean hasDisliked(final Room room) {
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
