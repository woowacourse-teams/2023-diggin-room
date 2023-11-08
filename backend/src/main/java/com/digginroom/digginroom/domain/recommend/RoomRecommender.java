package com.digginroom.digginroom.domain.recommend;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.domain.member.MemberGenre;
import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.exception.RecommendException.NoRecommendableRoomException;
import com.digginroom.digginroom.exception.RecommendException.UnderBoundWeightException;
import com.digginroom.digginroom.repository.RoomRepository;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomRecommender {

    private final RoomRepository roomRepository;

    public Room recommend(final Member member) {
        List<MemberGenre> memberGenres = member.getMemberGenres();
        Genre recommenedGenre = recommendGenre(memberGenres);
        return recommendRoom(recommenedGenre);
    }

    private Genre recommendGenre(final List<MemberGenre> memberGenres) {
        int weightSum = memberGenres.stream().mapToInt(MemberGenre::getWeight).sum();
        if (weightSum <= 0) {
            throw new UnderBoundWeightException(weightSum);
        }
        return pickByWeight(memberGenres, weightSum);
    }

    private Genre pickByWeight(final List<MemberGenre> memberGenres, final int weightSum) {
        int randomizedFactor = ThreadLocalRandom.current().nextInt(weightSum);
        int cursor = 0;
        for (MemberGenre weightedGenre : memberGenres) {
            cursor += weightedGenre.getWeight();
            if (randomizedFactor < cursor) {
                return weightedGenre.getGenre();
            }
        }
        return memberGenres.get(memberGenres.size() - 1).getGenre();
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
