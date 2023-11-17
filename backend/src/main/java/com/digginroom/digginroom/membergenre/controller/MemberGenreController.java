package com.digginroom.digginroom.membergenre.controller;

import com.digginroom.digginroom.controller.Auth;
import com.digginroom.digginroom.membergenre.service.MemberGenreService;
import com.digginroom.digginroom.membergenre.service.dto.MemberGenresResponse;
import com.digginroom.digginroom.service.dto.FavoriteGenresRequest;
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
public class MemberGenreController {

    private final MemberGenreService memberGenreService;

    @PostMapping("/favorite-genres")
    public ResponseEntity<Void> markFavorite(
            @Auth final Long memberId,
            @RequestBody final FavoriteGenresRequest favoriteGenresRequest
    ) {
        memberGenreService.markFavorite(memberId, favoriteGenresRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/genres")
    public ResponseEntity<MemberGenresResponse> showMemberDetails(@Auth final Long memberId) {
        boolean hasFavorite = memberGenreService.hasFavorite(memberId);
        return ResponseEntity.ok().body(new MemberGenresResponse(hasFavorite));
    }
}
