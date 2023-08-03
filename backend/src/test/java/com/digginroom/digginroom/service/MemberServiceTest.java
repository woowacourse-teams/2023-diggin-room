package com.digginroom.digginroom.service;

import static com.digginroom.digginroom.controller.TestFixture.파워;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.digginroom.digginroom.controller.TestFixture;
import com.digginroom.digginroom.controller.dto.MemberLoginRequest;
import com.digginroom.digginroom.controller.dto.MemberLoginResponse;
import com.digginroom.digginroom.controller.dto.MemberSaveRequest;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.exception.MemberException;
import com.digginroom.digginroom.repository.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberServiceTest {


    private static final String MEMBER_PASSWORD = "power1234@";
    private static final String MEMBER_USERNAME = "power2";

    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;

    @Test
    void 아이디가_중복이_아닌경우_회원가입을_성공한다() {
        when(memberRepository.existsByUsername(MEMBER_USERNAME))
                .thenReturn(false);

        memberService.save(new MemberSaveRequest(MEMBER_USERNAME, MEMBER_PASSWORD));

        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void 회원가입을_할_때_전달된_아이디가_중복일경우_에러가_발생한다() {
        when(memberRepository.existsByUsername(MEMBER_USERNAME))
                .thenReturn(true);

        assertThatThrownBy(() -> memberService.save(new MemberSaveRequest(MEMBER_USERNAME, MEMBER_PASSWORD)))
                .isInstanceOf(MemberException.DuplicationException.class)
                .hasMessageContaining("아이디가 중복되었습니다.");
    }

    @Test
    void 아이디가_중복이_아닌경우_에러가_발생하지않는다() {
        when(memberRepository.existsByUsername(MEMBER_USERNAME))
                .thenReturn(false);

        assertThat(memberService.checkDuplication(MEMBER_USERNAME).isDuplicated()).isFalse();
    }

    @Test
    void 회원_정보가_있는_경우_멤버를_반환한다() {
        Member power = new Member(MEMBER_USERNAME, MEMBER_PASSWORD);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(power));

        assertThat(memberService.findMember(1L)).isEqualTo(power);
    }

    @Test
    void 회원_정보가_없는_경우_에러가_발생한다() {
        when(memberRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.findMember(1L))
                .isInstanceOf(MemberException.NotFoundException.class)
                .hasMessageContaining("회원 정보가 없습니다.");
    }

    @Test
    void 회원_정보가_있다면_로그인_할_수_있다() {
        Member power = 파워();
        when(memberRepository.findMemberByUsername(power.getUsername())).thenReturn(Optional.of(power));

        assertThat(memberService.loginMember(TestFixture.MEMBER_LOGIN_REQUEST))
                .isEqualTo(new MemberLoginResponse(power.getId()));
    }

    @Test
    void 회원_정보가_없다면_로그인_할_수_없다() {
        when(memberRepository.findMemberByUsername(MEMBER_USERNAME)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.loginMember(new MemberLoginRequest(MEMBER_USERNAME, MEMBER_PASSWORD)))
                .isInstanceOf(MemberException.NotFoundException.class)
                .hasMessageContaining("회원 정보가 없습니다.");
    }

    @Test
    void 비밀번호가_틀리면_로그인_할_수_없다() {
        Member power = new Member(MEMBER_USERNAME, MEMBER_PASSWORD);
        when(memberRepository.findMemberByUsername(MEMBER_USERNAME)).thenReturn(Optional.of(power));

        assertThatThrownBy(() -> memberService.loginMember(
                new MemberLoginRequest(MEMBER_USERNAME, MEMBER_PASSWORD + "asd")))
                .isInstanceOf(MemberException.NotFoundException.class)
                .hasMessageContaining("회원 정보가 없습니다.");
    }
}
