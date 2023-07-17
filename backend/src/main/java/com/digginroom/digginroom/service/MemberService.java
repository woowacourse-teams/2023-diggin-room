package com.digginroom.digginroom.service;

import com.digginroom.digginroom.controller.dto.MemberRequest;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(final MemberRequest request) {
        validateMemberId(request.memberId());
        memberRepository.save(request.toMember());
    }

    public void validateMemberId(String memberId) {
        Optional<Member> findMember = memberRepository.findByMemberId(memberId);
        if (findMember.isPresent()) {
            throw new IllegalArgumentException("아이디가 중복되었습니다.");
        }
    }
}
