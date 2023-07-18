package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.controller.dto.MemberDuplicationResponse;
import com.digginroom.digginroom.controller.dto.MemberSaveRequest;
import com.digginroom.digginroom.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/join")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody @Valid final MemberSaveRequest memberSaveRequest) {
        memberService.save(memberSaveRequest);
    }

    @GetMapping("/checkMemberIdDuplication")
    public ResponseEntity<MemberDuplicationResponse> isDuplicated(@RequestParam final String memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.checkDuplication(memberId));
    }
}
