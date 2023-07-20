package com.digginroom.digginroom.service;

import static com.digginroom.digginroom.exception.MemberException.NotFoundException;

import com.digginroom.digginroom.controller.dto.MemberDuplicationResponse;
import com.digginroom.digginroom.controller.dto.MemberLoginRequest;
import com.digginroom.digginroom.controller.dto.MemberSaveRequest;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.exception.MemberException.DuplicationException;
import com.digginroom.digginroom.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

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

    public Member loginMember(final MemberLoginRequest request) {
        Optional<Member> findMember = memberRepository.findMemberByUsername(request.username());
        if (findMember.isEmpty()) {
            throw new NotFoundException();
        }

        Member member = findMember.get();
        if (isNotSamePassword(request, member)) {
            throw new NotFoundException();
        }
        return member;
    }

    private boolean isNotSamePassword(final MemberLoginRequest request, final Member member) {
        return !member.getPassword()
                .equals(request.password());
    }
}
