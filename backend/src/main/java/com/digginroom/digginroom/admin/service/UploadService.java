package com.digginroom.digginroom.admin.service;

import com.digginroom.digginroom.admin.controller.dto.UploadRequest;
import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.domain.track.Track;
import com.digginroom.digginroom.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final RoomRepository roomRepository;

    @Transactional
    public void save(final UploadRequest request) {
        Track track = Track.builder()
                .title(request.title())
                .artist(request.artist())
                .superGenre(request.superGenre())
                .subGenres(Arrays.asList(request.subGenre().split(",")))
                .description(request.description())
                .build();

        Room room = new Room(request.youtubeVideoId(), track);
        roomRepository.save(room);
    }
}
