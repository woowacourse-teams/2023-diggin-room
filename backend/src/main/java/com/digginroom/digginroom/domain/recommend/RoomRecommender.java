package com.digginroom.digginroom.domain.recommend;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.exception.RecommendException.NoRecommendableRoomException;
import com.digginroom.digginroom.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class RoomRecommender {

    private final RoomRepository roomRepository;
    private final GenreRecommender genreRecommender;

    public Room recommend(final Long memberId) {
        Genre recommenedGenre = genreRecommender.recommend(memberId);
        return recommendRoom(recommenedGenre);
    }

    private Room recommendRoom(final Genre recommendedGenre) {
        List<Room> rooms = roomRepository.findByTrackSuperGenre(recommendedGenre);
        if (rooms.isEmpty()) {
            throw new NoRecommendableRoomException(recommendedGenre.getName());
        }
        int pickedIndex = ThreadLocalRandom.current().nextInt(rooms.size());
        return rooms.get(pickedIndex);
    }
}
