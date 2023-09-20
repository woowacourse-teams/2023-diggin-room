package com.digginroom.digginroom.domain.member;

import com.digginroom.digginroom.domain.BaseEntity;
import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.exception.MemberException.DuplicatedFavoriteException;
import com.digginroom.digginroom.exception.MemberException.EmptyFavoriteException;
import com.digginroom.digginroom.exception.MemberException.FavoriteExistsException;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @NonNull
    private String username;
    @Embedded
    private Password password;
    @Enumerated(value = EnumType.STRING)
    private Provider provider;
    @Getter(AccessLevel.NONE)
    private boolean hasFavorite = false;
    @Embedded
    private Nickname nickname;
    @Embedded
    private final ScrapRooms scrapRooms = new ScrapRooms();
    @Embedded
    private final DislikeRooms dislikeRooms = new DislikeRooms();
    @Embedded
    private final MemberGenres memberGenres = new MemberGenres(this);

    private Member(String username, Password password, Provider provider, Nickname nickname) {
        this.username = username;
        this.password = password;
        this.provider = provider;
        this.nickname = nickname;
    }

    public static Member self(final String username, final String password) {
        return new Member(username, new Password(password), Provider.SELF, Nickname.random());
    }

    public static Member social(final String username, final Provider provider, final String nickname) {
        return new Member(username, Password.EMPTY, provider, Nickname.of(nickname));
    }

    public static Member guest() {
        return new Member(
                "guest-" + UUID.randomUUID().toString().split("-")[0],
                Password.EMPTY,
                Provider.SELF,
                Nickname.random()
        );
    }

    public void scrap(final Room room) {
        scrapRooms.scrap(room);
        adjustMemberGenreWeight(room, WeightFactor.SCRAP);
    }

    public void unscrap(final Room room) {
        scrapRooms.unscrap(room);
        adjustMemberGenreWeight(room, WeightFactor.UNSCRAP);
    }

    public void dislike(final Room room) {
        dislikeRooms.dislike(room);
        adjustMemberGenreWeight(room, WeightFactor.DISLIKE);
    }

    public void undislike(final Room room) {
        dislikeRooms.undislike(room);
        adjustMemberGenreWeight(room, WeightFactor.UNDISLIKE);
    }

    public void markFavorite(final List<Genre> genres) {
        if (hasFavorite) {
            throw new FavoriteExistsException();
        }
        if (hasDuplicate(genres)) {
            throw new DuplicatedFavoriteException();
        }
        if (genres.isEmpty()) {
            throw new EmptyFavoriteException();
        }

        for (Genre genre : genres) {
            memberGenres.adjustWeightBy(genre, WeightFactor.FAVORITE);
        }
        hasFavorite = true;
    }

    private boolean hasDuplicate(final List<Genre> genres) {
        return new HashSet<>(genres).size() != genres.size();
    }

    private void adjustMemberGenreWeight(final Room room, final WeightFactor weightFactor) {
        Genre superGenre = room.getTrack().getSuperGenre();
        memberGenres.adjustWeightBy(superGenre, weightFactor);
    }

    public boolean hasFavorite() {
        return hasFavorite;
    }

    public boolean hasScrapped(final Room pickedRoom) {
        return scrapRooms.hasScrapped(pickedRoom);
    }

    public List<MemberGenre> getMemberGenres() {
        return memberGenres.getAll();
    }

    public List<Room> getScrapRooms() {
        return scrapRooms.getScrapRooms();
    }

    public List<Room> getDislikeRooms() {
        return dislikeRooms.getDislikeRooms();
    }

    public String getNickname() {
        return nickname.getNickname();
    }
}
