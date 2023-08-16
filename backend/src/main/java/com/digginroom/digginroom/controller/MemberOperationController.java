package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.controller.dto.FavoriteGenresRequest;
import com.digginroom.digginroom.controller.dto.MemberDetailsResponse;
import com.digginroom.digginroom.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberOperationController {

    private final MemberService memberService;

    @PostMapping("/favorite-genres")
    public ResponseEntity<Void> markFavorite(
            @Auth final Long memberId,
            @RequestBody final FavoriteGenresRequest favoriteGenresRequest
    ) {
        memberService.markFavorite(memberId, favoriteGenresRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDetailsResponse> showMemberDetails(@Auth final Long memberId) {
        return ResponseEntity.ok().body(memberService.getMemberDetails(memberId));
    }
}
