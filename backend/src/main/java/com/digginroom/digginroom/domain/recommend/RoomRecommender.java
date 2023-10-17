package com.digginroom.digginroom.domain.recommend;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.domain.member.MemberGenre;
import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.repository.RoomRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RoomRecommender {

    public RoomRecommender(final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    private final RoomRepository roomRepository;

    private static final Random RANDOM = ThreadLocalRandom.current();

    public Room recommend(Member member) {
        List<MemberGenre> memberGenres = member.getMemberGenres();
        Genre recommenedGenre = recommendGenre(memberGenres);
        return recommendRoom(recommenedGenre);
    }

    private Genre recommendGenre(final List<MemberGenre> memberGenres) {
        int weightSum = memberGenres.stream().mapToInt(MemberGenre::getWeight).sum();
        if (weightSum <= 0) {
            throw new IllegalArgumentException("가중치 합이 0입니다");
        }
        int randomizedFactor = RANDOM.nextInt(weightSum);
        return pickByWeight(memberGenres, randomizedFactor);
    }

    private Room recommendRoom(final Genre recommendedGenre) {
        List<Room> rooms = roomRepository.findByTrackSuperGenre(recommendedGenre);
        int pickedIndex = ThreadLocalRandom.current().nextInt(rooms.size());

        return rooms.get(pickedIndex);
    }

    private Genre pickByWeight(final List<MemberGenre> memberGenres, final int randomizedFactor) {
        int cursor = 0;
        for (MemberGenre weightedGenre : memberGenres) {
            cursor += weightedGenre.getWeight();
            if (randomizedFactor < cursor) {
                return weightedGenre.getGenre();
            }
        }
        return memberGenres.get(memberGenres.size() - 1).getGenre();
    }
}
