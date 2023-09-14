package com.digginroom.digginroom.domain.member;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.exception.GenreException.MemberGenreNotFoundException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGenres {

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<MemberGenre> memberGenres;

    public MemberGenres(final Member member) {
        memberGenres = Arrays.stream(Genre.values())
                .map(genre -> new MemberGenre(genre, member))
                .toList();
    }

    public void adjustWeightBy(final Genre genre, final WeightFactor weightFactor) {
        MemberGenre targetMemberGenre = getTargetMemberGenre(genre);
        targetMemberGenre.adjustWeight(weightFactor);
    }

    private MemberGenre getTargetMemberGenre(final Genre genre) {
        return memberGenres.stream()
                .filter(memberGenre -> memberGenre.isSameGenre(genre))
                .findFirst()
                .orElseThrow(() -> new MemberGenreNotFoundException(genre));
    }

    public List<MemberGenre> getAll() {
        return memberGenres;
    }
}
