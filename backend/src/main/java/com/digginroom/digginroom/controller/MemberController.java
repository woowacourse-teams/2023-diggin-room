package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.controller.dto.MemberDuplicationResponse;
import com.digginroom.digginroom.controller.dto.MemberLoginRequest;
import com.digginroom.digginroom.controller.dto.MemberSaveRequest;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/join")
@RequiredArgsConstructor
public class MemberController {

    private static final int PERSISTENT_TIME = 0;

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid final MemberSaveRequest memberSaveRequest) {
        memberService.save(memberSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/exist")
    public ResponseEntity<MemberDuplicationResponse> isDuplicated(@RequestParam final String username) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.checkDuplication(username));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody @Valid
            final MemberLoginRequest memberLoginRequest,
            final HttpSession httpSession
    ) {
        Member member = memberService.loginMember(memberLoginRequest);
        httpSession.setAttribute("memberId", member.getId());
        httpSession.setMaxInactiveInterval(PERSISTENT_TIME);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
