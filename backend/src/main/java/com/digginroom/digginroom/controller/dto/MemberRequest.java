package com.digginroom.digginroom.controller.dto;

import com.digginroom.digginroom.domain.Member;
import jakarta.validation.constraints.NotNull;

public record MemberRequest(
        @NotNull(message = "아이디를 입력해주세요.") String memberId,
        @NotNull(message = "비밀번호를 입력해주세요.") String password
) {

    public Member toMember() {
        return new Member(memberId, password);
    }
}
