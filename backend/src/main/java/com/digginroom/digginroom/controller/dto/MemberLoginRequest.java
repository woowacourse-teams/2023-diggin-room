package com.digginroom.digginroom.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberLoginRequest(
        @NotBlank(message = "아이디를 입력해주세요.")
        @Size(min = 5, max = 20, message = "아이디는 5~20자만 가능합니다.")
        @Pattern(
                regexp = "^[a-zA-Z0-9]+$",
                message = "아이디는 영문자, 숫자만 입력해주세요."
        )
        String username,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, max = 16, message = "비밀번호는 8~16자만 가능합니다.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]*$",
                message = "비밀번호는 영문자, 숫자, 특수문자를 1개 이상 입력해주세요."
        )
        String password
) {
}
