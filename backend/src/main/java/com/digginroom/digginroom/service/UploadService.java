package com.digginroom.digginroom.service;

import com.digginroom.digginroom.controller.UploadRequestDto;
import com.digginroom.digginroom.domain.MediaSource;
import com.digginroom.digginroom.domain.Room;
import com.digginroom.digginroom.domain.Track;
import com.digginroom.digginroom.repository.RoomRepository;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final RoomRepository roomRepository;

    @Transactional
    public void save(final UploadRequestDto request) {
        MediaSource mediaSource = new MediaSource(request.youtubeVideoId());
        Track track = Track.builder()
                .title(request.title())
                .artist(request.artist())
                .superGenre(request.superGenre())
                .subGenres(Arrays.asList(request.subGenre().split(",")))
                .description(request.description())
                .build();

        Room room = new Room(mediaSource, track);
        roomRepository.save(room);
    }
}
