package com.digginroom.digginroom.service;

import com.digginroom.digginroom.controller.dto.RoomResponse;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.domain.Room;
import com.digginroom.digginroom.exception.RoomException.EmptyException;
import com.digginroom.digginroom.exception.RoomException.NotFoundException;
import com.digginroom.digginroom.repository.RoomRepository;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberService memberService;

    public RoomResponse pickRandom(final Long memberId) {
        Room pickedRoom = getRandomRoom();

        Member member = memberService.findMember(memberId);

        return new RoomResponse(
                pickedRoom.getId(),
                pickedRoom.getMediaSource().getIdentifier(),
                member.hasScrapped(pickedRoom)
        );
    }

    private Room getRandomRoom() {
        int count = Math.toIntExact(roomRepository.count());

        Page<Room> randomizedPage = roomRepository.findAll(
                Pageable.ofSize(1)
                        .withPage(ThreadLocalRandom.current().nextInt(count))
        );

        return randomizedPage.getContent()
                .stream()
                .findFirst()
                .orElseThrow(EmptyException::new);
    }

    @Transactional
    public void scrap(final Long memberId, final Long roomId) {
        Room room = findRoom(roomId);
        Member member = memberService.findMember(memberId);

        member.scrap(room);
    }

    @Transactional
    public void unscrap(final Long memberId, final Long roomId) {
        Room room = findRoom(roomId);
        Member member = memberService.findMember(memberId);

        member.unscrap(room);
    }

    private Room findRoom(final Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException(roomId));
    }

    @Transactional
    public void dislike(final Long memberId, final Long roomId) {
        Room room = findRoom(roomId);
        Member member = memberService.findMember(memberId);

        member.dislike(room);
    }
}
