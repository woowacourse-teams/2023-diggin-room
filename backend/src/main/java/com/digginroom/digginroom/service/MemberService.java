package com.digginroom.digginroom.service;

import com.digginroom.digginroom.controller.dto.FavoriteGenresRequest;
import com.digginroom.digginroom.controller.dto.GoogleOAuthRequest;
import com.digginroom.digginroom.controller.dto.MemberDuplicationResponse;
import com.digginroom.digginroom.controller.dto.MemberLoginRequest;
import com.digginroom.digginroom.controller.dto.MemberLoginResponse;
import com.digginroom.digginroom.controller.dto.MemberSaveRequest;
import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.domain.Provider;
import com.digginroom.digginroom.exception.MemberException.DuplicationException;
import com.digginroom.digginroom.exception.MemberException.NotFoundException;
import com.digginroom.digginroom.exception.MemberException.WrongProviderException;
import com.digginroom.digginroom.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final OAuthUsernameResolver googleUsernameRetriever;

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
    public MemberLoginResponse loginMember(final MemberLoginRequest request) {
        Member member = memberRepository.findMemberByUsername(request.username())
                .orElseThrow(NotFoundException::new);

        if (member.getProvider() != Provider.SELF) {
            throw new WrongProviderException();
        }

        if (member.getPassword().doesNotMatch(request.password())) {
            throw new NotFoundException();
        }
        return new MemberLoginResponse(member.getId());
    }

    public MemberLoginResponse loginMember(final GoogleOAuthRequest request) {
        String googleUsername = googleUsernameRetriever.resolve(request.idToken());

        Member member = memberRepository.findMemberByUsername(googleUsername)
                .orElseGet(() -> memberRepository.save(
                        new Member(googleUsername, Provider.GOOGLE))
                );

        return new MemberLoginResponse(member.getId());
    }
}
