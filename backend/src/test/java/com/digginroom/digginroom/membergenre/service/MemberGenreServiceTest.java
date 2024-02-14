package com.digginroom.digginroom.membergenre.service;

import com.digginroom.digginroom.controller.ControllerTest;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.domain.room.Genre;
import com.digginroom.digginroom.membergenre.domain.MemberGenre;
import com.digginroom.digginroom.membergenre.domain.MemberGenreEvent;
import com.digginroom.digginroom.membergenre.domain.MemberGenreRepository;
import com.digginroom.digginroom.membergenre.domain.MemberGenres;
import com.digginroom.digginroom.membergenre.domain.vo.Weight;
import com.digginroom.digginroom.membergenre.domain.vo.WeightFactor;
import com.digginroom.digginroom.membergenre.domain.vo.WeightStatus;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.service.dto.FavoriteGenresRequest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.digginroom.digginroom.TestFixture.파워;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberGenreServiceTest extends ControllerTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberGenreRepository memberGenreRepository;
    @Autowired
    private MemberGenreService memberGenreService;

    @Test
    void 멤버_장르_가중치가_없다면_FALSE를_반환한다() {
        assertThat(memberGenreService.hasFavorite(Long.MAX_VALUE)).isFalse();
    }

    @Test
    void 멤버_장르_가중치가_있다면_TRUE를_반환한다() {
        Member member = memberRepository.save(파워());
        memberGenreRepository.saveAll(new MemberGenres(member.getId()).getMemberGenres());

        assertThat(memberGenreService.hasFavorite(member.getId())).isTrue();
    }

    @Test
    void 멤버_장르_가중치를_조절한다() {
        Member member = memberRepository.save(파워());
        memberGenreRepository.saveAll(new MemberGenres(member.getId()).getMemberGenres());

        memberGenreService.adjustMemberGenreWeight(new MemberGenreEvent() {
            @Override
            public Genre getGenre() {
                return Genre.ROCK;
            }

            @Override
            public Long getMemberId() {
                return member.getId();
            }

            @Override
            public WeightFactor getWeightFactor() {
                return WeightFactor.SCRAP;
            }
        });

        MemberGenre memberGenre = getMemberGenre(
                memberGenreRepository.findByMemberId(member.getId()),
                Genre.ROCK
        );
        assertThat(memberGenre.getWeight())
                .isEqualTo(new Weight(WeightStatus.DEFAULT.getValue() + WeightFactor.SCRAP.getValue()));
    }

    private MemberGenre getMemberGenre(final List<MemberGenre> memberGenres, final Genre genre) {
        return memberGenres
                .stream()
                .filter(it -> it.isSameGenre(genre))
                .findFirst()
                .get();
    }

    @Test
    void 멤버_장르_가중치를_생성하고_선호도를_반영한다() {
        Member member = memberRepository.save(파워());
        FavoriteGenresRequest request = new FavoriteGenresRequest(List.of(Genre.ROCK, Genre.POP, Genre.BLUES));

        memberGenreService.markFavorite(member.getId(), request);

        List<MemberGenre> memberGenres = memberGenreRepository.findByMemberId(member.getId());
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(memberGenres).map(MemberGenre::getWeight).allMatch(it -> it.getValue() >= WeightStatus.DEFAULT.getValue());
            softly.assertThat(getMemberGenre(memberGenres, Genre.ROCK).getWeight()).isEqualTo(new Weight(11000));
            softly.assertThat(getMemberGenre(memberGenres, Genre.POP).getWeight()).isEqualTo(new Weight(11000));
            softly.assertThat(getMemberGenre(memberGenres, Genre.BLUES).getWeight()).isEqualTo(new Weight(11000));
        });
    }
}
