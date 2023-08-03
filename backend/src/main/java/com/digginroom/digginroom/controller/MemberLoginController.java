package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.controller.dto.MemberLoginRequest;
import com.digginroom.digginroom.controller.dto.MemberLoginResponse;
import com.digginroom.digginroom.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class MemberLoginController {

    private static final int PERSISTENT_TIME = 0;

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> login(
            @RequestBody @Valid final MemberLoginRequest memberLoginRequest,
            final HttpSession httpSession
    ) {
        MemberLoginResponse member = memberService.loginMember(memberLoginRequest);

        httpSession.setAttribute("memberId", member.memberId());
        httpSession.setMaxInactiveInterval(PERSISTENT_TIME);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
