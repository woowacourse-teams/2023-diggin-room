package com.digginroom.digginroom.member.repository;

import static com.digginroom.digginroom.TestFixture.파워;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.exception.MemberException;
import com.digginroom.digginroom.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void 멤버_저장_시_멤버장르들도_같이_저장된다() {
        Member 파워 = 파워();

        Member saved파워 = memberRepository.save(파워);

        assertThat(saved파워.getMemberGenres()).hasSize(Genre.values().length);
    }

    @Test
    void 멤버_저장_후_조회하면_멤버_장르들도_같이_조회된다() {
        Member 파워 = 파워();
        Member saved파워 = memberRepository.save(파워);
        entityManager.clear();

        Member found = memberRepository.findById(saved파워.getId()).get();

        assertThat(found.getMemberGenres()).hasSize(Genre.values().length);
    }

    @Test
    void 회원_정보가_없는_경우_에러가_발생한다() {
        assertThatThrownBy(() -> memberRepository.getMemberById(Long.MAX_VALUE))
                .isInstanceOf(MemberException.NotFoundException.class);
    }
}
