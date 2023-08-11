package com.digginroom.digginroom.service;

import com.digginroom.digginroom.controller.dto.GoogleOAuthRequest;
import com.digginroom.digginroom.controller.dto.MemberDuplicationResponse;
import com.digginroom.digginroom.controller.dto.MemberLoginRequest;
import com.digginroom.digginroom.controller.dto.MemberLoginResponse;
import com.digginroom.digginroom.controller.dto.MemberSaveRequest;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.domain.Provider;
import com.digginroom.digginroom.exception.MemberException.DuplicationException;
import com.digginroom.digginroom.exception.MemberException.NotFoundException;
import com.digginroom.digginroom.exception.MemberException.WrongProviderException;
import com.digginroom.digginroom.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final OAuthUsernameResolver googleUsernameRetriever;

    @Transactional
    public void save(final MemberSaveRequest request) {
        if (isDuplicated(request.username())) {
            throw new DuplicationException();
        }
        memberRepository.save(request.toMember());
    }

    private boolean isDuplicated(final String username) {
        return memberRepository.existsByUsername(username);
    }

    public MemberDuplicationResponse checkDuplication(final String username) {
        boolean duplicated = isDuplicated(username);
        return new MemberDuplicationResponse(duplicated);
    }

    public Member findMember(final Long id) {
        return memberRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

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
