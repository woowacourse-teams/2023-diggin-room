package com.digginroom.digginroom.service;

import com.digginroom.digginroom.controller.dto.*;
import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.domain.member.Provider;
import com.digginroom.digginroom.exception.MemberException.DuplicationException;
import com.digginroom.digginroom.exception.MemberException.NotFoundException;
import com.digginroom.digginroom.exception.MemberException.WrongProviderException;
import com.digginroom.digginroom.oauth.IdTokenResolver;
import com.digginroom.digginroom.oauth.payload.IdTokenPayload;
import com.digginroom.digginroom.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final IdTokenResolver idTokenResolver;


    public void save(final MemberSaveRequest request) {
        if (isDuplicated(request.username())) {
            throw new DuplicationException();
        }
        memberRepository.save(request.toMember());
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
        Member member = findMember(memberId);

        List<Genre> favoriteGenres = genres.favoriteGenres().stream()
                .map(Genre::of)
                .toList();
        member.markFavorite(favoriteGenres);
    }

    @Transactional(readOnly = true)
    public Member findMember(final Long id) {
        return memberRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public MemberDetailsResponse getMemberDetails(final Long id) {
        return MemberDetailsResponse.of(findMember(id));
    }

    @Transactional(readOnly = true)
    public MemberLoginResponse loginMember(final MemberLoginRequest request) {
        Member member = memberRepository.findMemberByUsername(request.username())
                .orElseThrow(NotFoundException::new);

        if (member.getProvider() != Provider.SELF) {
            throw new WrongProviderException();
        }

        if (member.getPassword().doesNotMatch(request.password())) {
            throw new NotFoundException();
        }
        return MemberLoginResponse.of(member);
    }

    public MemberLoginResponse loginMember(final String idToken) {
        IdTokenPayload payload = idTokenResolver.resolve(idToken);

        Member member = memberRepository.findMemberByUsername(payload.getUsername())
                .orElseGet(() -> memberRepository.save(new Member(
                        payload.getUsername(),
                        payload.getProvider(),
                        payload.getNickname()
                )));

        return MemberLoginResponse.of(member);
    }
}
