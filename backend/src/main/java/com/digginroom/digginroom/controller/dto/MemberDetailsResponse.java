package com.digginroom.digginroom.controller.dto;

import com.digginroom.digginroom.domain.Member;

public record MemberDetailsResponse(Long memberId, String username, boolean hasFavorite) {

    public static MemberDetailsResponse of(final Member member) {
        return new MemberDetailsResponse(
                member.getId(),
                member.getUsername(),
                member.hasFavorite()
        );
    }
}
