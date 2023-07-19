package com.digginroom.digginroom.service;

import com.digginroom.digginroom.controller.dto.MemberDuplicationResponse;
import com.digginroom.digginroom.controller.dto.MemberSaveRequest;
import com.digginroom.digginroom.exception.MemberException.DuplicationException;
import com.digginroom.digginroom.repository.MemberRepository;
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
}
