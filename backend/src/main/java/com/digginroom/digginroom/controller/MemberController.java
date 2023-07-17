package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.controller.dto.MemberRequest;
import com.digginroom.digginroom.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/join")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid final MemberRequest memberRequest) {
        memberService.save(memberRequest);
    }

    @GetMapping("/checkMemberIdDuplication")
    public void isDuplicated(@RequestParam final String memberId) {
        memberService.validateMemberId(memberId);
    }
}
