package com.digginroom.digginroom.service;

import static com.digginroom.digginroom.TestFixture.ID_TOKEN_PAYLOAD;
import static com.digginroom.digginroom.TestFixture.파워;
import static com.digginroom.digginroom.domain.Genre.DANCE;
import static com.digginroom.digginroom.domain.Genre.ROCK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.digginroom.digginroom.service.dto.FavoriteGenresRequest;
import com.digginroom.digginroom.service.dto.MemberLoginRequest;
import com.digginroom.digginroom.service.dto.MemberLoginResponse;
import com.digginroom.digginroom.service.dto.MemberSaveRequest;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.exception.MemberException;
import com.digginroom.digginroom.oauth.IdTokenResolver;
import com.digginroom.digginroom.repository.MemberRepository;
import java.util.List;
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

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private IdTokenResolver idTokenResolver;
    @InjectMocks
    private MemberService memberService;

    @Test
    void 아이디가_중복이_아닌경우_회원가입을_성공한다() {
        when(memberRepository.existsByUsername("power"))
                .thenReturn(false);

        memberService.save(new MemberSaveRequest("power", "power"));

        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void 회원가입을_할_때_전달된_아이디가_중복일경우_에러가_발생한다() {
        when(memberRepository.existsByUsername("power"))
                .thenReturn(true);

        assertThatThrownBy(() -> memberService.save(new MemberSaveRequest("power", "power")))
                .isInstanceOf(MemberException.DuplicationException.class)
                .hasMessageContaining("아이디가 중복되었습니다.");
    }

    @Test
    void 아이디가_중복이_아닌경우_에러가_발생하지않는다() {
        when(memberRepository.existsByUsername("power"))
                .thenReturn(false);

        assertThat(memberService.checkDuplication("power").isDuplicated()).isFalse();
    }

    @Test
    void 회원_정보가_있다면_로그인_할_수_있다() {
        Member power = Member.self("power", "power123!");
        when(memberRepository.getMemberByUsername("power")).thenReturn(power);

        assertThat(memberService.loginMember(new MemberLoginRequest(power.getUsername(), "power123!")))
                .isEqualTo(MemberLoginResponse.of(power));
    }

    @Test
    void 회원_정보가_없다면_로그인_할_수_없다() {
        when(memberRepository.getMemberByUsername("power")).thenThrow(MemberException.NotFoundException.class);

        assertThatThrownBy(() -> memberService.loginMember(new MemberLoginRequest("power", "power123!")))
                .isInstanceOf(MemberException.NotFoundException.class);
    }

    @Test
    void 비밀번호가_틀리면_로그인_할_수_없다() {
        Member power = Member.self("power", "power123!");
        when(memberRepository.getMemberByUsername("power")).thenReturn(power);

        assertThatThrownBy(() -> memberService.loginMember(
                new MemberLoginRequest(power.getUsername(), power.getPassword() + "asd")))
                .isInstanceOf(MemberException.NotFoundException.class);
    }

    @Test
    void OAuth_로_처음_로그인한_유저는_유저_정보가_생성된다() {
        Member member = 파워();
        when(idTokenResolver.resolve(any())).thenReturn(ID_TOKEN_PAYLOAD);
        when(memberRepository.findMemberByUsername(member.getUsername())).thenReturn(Optional.empty());
        when(memberRepository.save(any())).thenReturn(member);

        MemberLoginResponse response = memberService.loginMember("ID_TOKEN");

        verify(memberRepository, times(1)).save(any());
        assertThat(response).isNotNull();
    }

    @Test
    void OAuth_로_이미_로그인했던_유저는_로그인할_수_있다() {
        Member member = 파워();
        when(idTokenResolver.resolve(any())).thenReturn(ID_TOKEN_PAYLOAD);
        when(memberRepository.findMemberByUsername(member.getUsername())).thenReturn(Optional.of(member));

        MemberLoginResponse response = memberService.loginMember("ID_TOKEN");

        verify(memberRepository, times(0)).save(any());
        assertThat(response).isNotNull();
    }

    @Test
    void 취향_정보를_입력한다() {
        Member member = 파워();
        when(memberRepository.getMemberById(member.getId())).thenReturn(member);

        memberService.markFavorite(member.getId(), new FavoriteGenresRequest(List.of(DANCE.getName(), ROCK.getName())));

        assertThat(member.hasFavorite()).isTrue();
    }

    @Test
    void 아이디_유저네임_취향정보수집여부를_포함한_회원정보를_반환한다() {
        Member member = 파워();
        when(memberRepository.getMemberById(member.getId())).thenReturn(member);

        assertThat(memberService.getMemberDetails(member.getId()))
                .extracting("memberId", "username", "hasFavorite")
                .containsExactly(member.getId(), member.getUsername(), member.hasFavorite());
    }

    @Test
    void 게스트로_로그인한다() {
        when(memberRepository.save(any(Member.class))).thenReturn(Member.guest());

        memberService.loginGuest();
        verify(memberRepository).save(any(Member.class));
    }
}
