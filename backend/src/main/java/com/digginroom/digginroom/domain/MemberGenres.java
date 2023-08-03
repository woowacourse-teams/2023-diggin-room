package com.digginroom.digginroom.domain;

import com.digginroom.digginroom.exception.MemberGenreException.NotFoundException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGenres {

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<MemberGenre> memberGenres;

    public MemberGenres(final Member member) {
        memberGenres = Arrays.stream(Genre.values())
                .map(it -> new MemberGenre(it, member))
                .toList();
    }

    public void adjustWeightBy(final Genre genre, final Weight weight) {
        MemberGenre targetMemberGenre = getTargetMemberGenre(genre);
        targetMemberGenre.adjustWeight(weight);
    }

    private MemberGenre getTargetMemberGenre(final Genre genre) {
        return memberGenres.stream()
                .filter(it -> it.isSameGenre(genre))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(genre));
    }
}
