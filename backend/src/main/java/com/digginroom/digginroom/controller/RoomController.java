package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.controller.dto.RoomResponse;
import com.digginroom.digginroom.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/room")
    public RoomResponse showRandomRoom(@Auth final Long memberId) {
        return roomService.pickRandom(memberId);
    }
}
