package com.digginroom.digginroom.service;

import com.digginroom.digginroom.controller.dto.RoomResponse;
import com.digginroom.digginroom.domain.Room;
import com.digginroom.digginroom.exception.RoomException.EmptyException;
import com.digginroom.digginroom.repository.RoomRepository;
import java.util.Collections;
import java.util.List;
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

    public RoomResponse pickRandom() {
        List<Room> rooms = roomRepository.findAll();
        Collections.shuffle(rooms);

        Room pickedRoom = rooms.stream()
                .findFirst()
                .orElseThrow(EmptyException::new);

        return new RoomResponse(pickedRoom.getId(), pickedRoom.getMediaSource().getIdentifier());
    }

    public Room pickRandomByPage() {
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
}
