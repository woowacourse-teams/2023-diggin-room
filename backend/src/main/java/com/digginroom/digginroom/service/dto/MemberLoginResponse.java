package com.digginroom.digginroom.service.dto;

import com.digginroom.digginroom.domain.member.Member;

public record MemberLoginResponse(Long memberId) {

    public static MemberLoginResponse of(final Member member) {
        return new MemberLoginResponse(
                member.getId()
        );
    }
}
