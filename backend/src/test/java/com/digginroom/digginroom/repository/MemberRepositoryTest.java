package com.digginroom.digginroom.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.digginroom.digginroom.exception.MemberException;
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

    @Test
    void 회원_정보가_없는_경우_에러가_발생한다() {
        assertThatThrownBy(() -> memberRepository.getMemberById(Long.MAX_VALUE))
                .isInstanceOf(MemberException.NotFoundException.class);
    }
}
