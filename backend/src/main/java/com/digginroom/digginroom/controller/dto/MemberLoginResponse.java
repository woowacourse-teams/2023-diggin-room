package com.digginroom.digginroom.controller.dto;

import com.digginroom.digginroom.domain.Member;

public record MemberLoginResponse(Long memberId, boolean hasFavorite) {

    public static MemberLoginResponse of(Member member) {
        return new MemberLoginResponse(
                member.getId(),
                member.hasFavorite()
        );
    }
}
