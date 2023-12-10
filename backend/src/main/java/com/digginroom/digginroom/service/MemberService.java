package com.digginroom.digginroom.service;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.domain.member.Password;
import com.digginroom.digginroom.domain.member.Provider;
import com.digginroom.digginroom.exception.MemberException.DuplicationException;
import com.digginroom.digginroom.exception.MemberException.NotFoundException;
import com.digginroom.digginroom.exception.MemberException.WrongProviderException;
import com.digginroom.digginroom.oauth.IdTokenResolver;
import com.digginroom.digginroom.oauth.payload.IdTokenPayload;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.service.dto.FavoriteGenresRequest;
import com.digginroom.digginroom.service.dto.MemberDetailsResponse;
import com.digginroom.digginroom.service.dto.MemberDuplicationResponse;
import com.digginroom.digginroom.service.dto.MemberLoginRequest;
import com.digginroom.digginroom.service.dto.MemberLoginResponse;
import com.digginroom.digginroom.service.dto.MemberSaveRequest;
import com.digginroom.digginroom.util.PasswordEncoder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final IdTokenResolver idTokenResolver;
    private final PasswordEncoder passwordEncoder;


    public void save(final MemberSaveRequest request) {
        if (isDuplicated(request.username())) {
            throw new DuplicationException();
        }
        Password password = new Password(request.password(), passwordEncoder);
        memberRepository.save(Member.self(request.username(), password));
    }

    private boolean isDuplicated(final String username) {
        return memberRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public MemberDuplicationResponse checkDuplication(final String username) {
        boolean duplicated = isDuplicated(username);
        return new MemberDuplicationResponse(duplicated);
    }

    public void markFavorite(final Long memberId, final FavoriteGenresRequest genres) {
        Member member = memberRepository.getMemberById(memberId);

        List<Genre> favoriteGenres = genres.favoriteGenres().stream()
                .map(Genre::of)
                .toList();
        member.markFavorite(favoriteGenres);
    }

    @Transactional(readOnly = true)
    public MemberDetailsResponse getMemberDetails(final Long id) {
        return MemberDetailsResponse.of(memberRepository.getMemberById(id));
    }

    public MemberLoginResponse loginGuest() {
        Member member = memberRepository.save(Member.guest());
        return MemberLoginResponse.of(member);
    }

    @Transactional(readOnly = true)
    public MemberLoginResponse loginMember(final MemberLoginRequest request) {
        Member member = memberRepository.getMemberByUsername(request.username());

        if (member.getProvider() != Provider.SELF) {
            throw new WrongProviderException();
        }

        if (member.getPassword().doesNotMatch(request.password(), passwordEncoder)) {
            throw new NotFoundException();
        }
        return MemberLoginResponse.of(member);
    }

    public MemberLoginResponse loginMember(final String idToken) {
        IdTokenPayload payload = idTokenResolver.resolve(idToken);

        Member member = memberRepository.findMemberByUsername(payload.getUsername())
                .orElseGet(() -> memberRepository.save(Member.social(
                        payload.getUsername(),
                        payload.getProvider(),
                        payload.getNickname()
                )));
        return MemberLoginResponse.of(member);
    }
}
