package com.digginroom.digginroom.service;

import com.digginroom.digginroom.controller.dto.MemberSaveRequest;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.exception.MemberException;
import com.digginroom.digginroom.repository.MemberRepository;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;

    @Test
    void 아이디가_중복일경우_에러가_발생한다() {
        when(memberRepository.findByMemberId("power"))
                .thenReturn(Optional.of(new Member("power", "power")));

        assertThatThrownBy(() -> memberService.validateMemberId("power"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("아이디가 중복되었습니다.");
    }

    @Test
    void 아이디가_중복이_아닌경우_에러가_발생하지않는다() {
        when(memberRepository.findByMemberId("power"))
                .thenReturn(Optional.empty());

        assertThat(memberService.checkDuplication("power").isDuplicated()).isFalse();
    }
}
