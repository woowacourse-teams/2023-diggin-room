package com.digginroom.digginroom.controller;

import com.digginroom.digginroom.controller.dto.RoomRequest;
import com.digginroom.digginroom.controller.dto.RoomResponse;
import com.digginroom.digginroom.controller.dto.ScrappedRoomsResponse;
import com.digginroom.digginroom.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<RoomResponse> showRecommendedRoom(@Auth final Long memberId) {
        return ResponseEntity.ok().body(roomService.recommend(memberId));
    }

    @GetMapping("/scrap")
    public ResponseEntity<ScrappedRoomsResponse> findScrapRooms(@Auth final Long memberId) {
        ScrappedRoomsResponse scrappedRooms = roomService.findScrappedRooms(memberId);
        return ResponseEntity.ok().body(scrappedRooms);
    }

    @PostMapping("/scrap")
    public ResponseEntity<Void> scrap(@Auth final Long memberId, @RequestBody final RoomRequest roomRequest) {
        roomService.scrap(memberId, roomRequest.roomId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/scrap")
    public ResponseEntity<Void> unscrap(@Auth final Long memberId, @RequestBody final RoomRequest roomRequest) {
        roomService.unscrap(memberId, roomRequest.roomId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    public ResponseEntity<Void> dislike(@Auth final Long memberId, @RequestBody final RoomRequest roomRequest) {
        roomService.dislike(memberId, roomRequest.roomId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/dislike")
    public ResponseEntity<Void> unDislike(@Auth final Long memberId, @RequestBody final RoomRequest roomRequest) {
        roomService.undislike(memberId, roomRequest.roomId());
        return ResponseEntity.ok().build();
    }
}
