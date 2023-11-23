package com.digginroom.digginroom.membergenre.domain;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.exception.GenreException.MemberGenreNotFoundException;
import com.digginroom.digginroom.exception.MemberException.EmptyFavoriteException;
import com.digginroom.digginroom.exception.MemberException.FavoriteExistsException;
import com.digginroom.digginroom.membergenre.domain.vo.WeightFactor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class MemberGenres {

    private List<MemberGenre> memberGenres;

    public MemberGenres(final List<MemberGenre> memberGenres) {
        validateHasMemberGenres(memberGenres);
        this.memberGenres = memberGenres;
    }

    private void validateHasMemberGenres(final List<MemberGenre> memberGenres) {
        if (memberGenres.isEmpty()) {
            throw new EmptyFavoriteException();
        }
    }

    public void adjustWeight(final Genre genre, final WeightFactor weightFactor) {
        memberGenres.stream()
                .filter(memberGenre -> memberGenre.isSameGenre(genre))
                .findFirst()
                .orElseThrow(() -> new MemberGenreNotFoundException(genre))
                .adjustWeight(weightFactor);
    }

    public void markFavorites(final List<Genre> genres) {
        validateDuplicate(genres);
        if (genres.isEmpty()) {
            throw new EmptyFavoriteException();
        }

        genres.forEach(this::markFavorite);
    }

    private void validateDuplicate(final List<Genre> genres) {
        if (new HashSet<>(genres).size() != genres.size()) {
            throw new FavoriteExistsException();
        }
    }

    private void markFavorite(final Genre genre) {
        memberGenres.stream()
                .filter(it -> it.isSameGenre(genre))
                .findFirst()
                .orElseThrow()
                .adjustWeight(WeightFactor.FAVORITE);
    }

    public static MemberGenres createMemberGenres(final Long memberId) {
        return new MemberGenres(Arrays.stream(Genre.values())
                .map(genre -> new MemberGenre(genre, memberId))
                .toList()
        );
    }

    public List<MemberGenre> getMemberGenres() {
        return List.copyOf(memberGenres);
    }
}
