package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.controller.dto.RoomRequest;
import com.digginroom.digginroom.controller.dto.RoomResponse;
import com.digginroom.digginroom.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/room")
    public RoomResponse showRandomRoom(@Auth final Long memberId) {
        return roomService.pickRandom(memberId);
    }

    @PostMapping("/scrap")
    @ResponseStatus(HttpStatus.CREATED)
    public void scrap(@Auth final Long memberId, @RequestBody RoomRequest roomRequest) {
        roomService.scrap(memberId, roomRequest.roomId());
    }

    @DeleteMapping("/scrap")
    public void showRandomRoom(@Auth final Long memberId, @RequestBody RoomRequest roomRequest) {
        roomService.unscrap(memberId, roomRequest.roomId());
    }
}
