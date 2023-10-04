package com.digginroom.digginroom.service;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.exception.RoomException.NotFoundException;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.repository.RoomRepository;
import com.digginroom.digginroom.service.dto.RoomResponse;
import com.digginroom.digginroom.service.dto.RoomsResponse;
import com.digginroom.digginroom.service.dto.TrackResponse;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public RoomResponse recommend(final Long memberId) {
        Member member = memberRepository.getMemberById(memberId);

        try {
            Genre recommendedGenre = new GenreRecommender().recommend(member.getMemberGenres());
            Room recommendedRoom = recommendRoom(recommendedGenre);

            return new RoomResponse(
                    recommendedRoom.getId(),
                    recommendedRoom.getIdentifier(),
                    member.hasScrapped(recommendedRoom),
                    recommendedRoom.getScrapCount(),
                    TrackResponse.of(recommendedRoom.getTrack())
            );
        } catch (IllegalArgumentException e) {
            return this.recommend(memberId);
        }
    }

    private Room recommendRoom(final Genre recommendedGenre) {
        List<Room> rooms = roomRepository.findByTrackSuperGenre(recommendedGenre);
        int pickedIndex = ThreadLocalRandom.current().nextInt(rooms.size());

        return rooms.get(pickedIndex);
    }

    @Transactional(readOnly = true)
    public RoomsResponse findScrappedRooms(final Long memberId) {
        Member member = memberRepository.getMemberById(memberId);
        return new RoomsResponse(member.getScrapRooms().stream()
                .map(room -> new RoomResponse(
                        room.getId(),
                        room.getIdentifier(),
                        member.hasScrapped(room),
                        room.getScrapCount(),
                        TrackResponse.of(room.getTrack())
                ))
                .toList());
    }

    public void scrap(final Long memberId, final Long roomId) {
        Room room = findRoom(roomId);
        Member member = memberRepository.getMemberById(memberId);
        member.scrap(room);
    }

    public void unscrap(final Long memberId, final Long roomId) {
        Room room = findRoom(roomId);
        Member member = memberRepository.getMemberById(memberId);
        member.unscrap(room);
    }

    private Room findRoom(final Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException(roomId));
    }

    public void dislike(final Long memberId, final Long roomId) {
        Room room = findRoom(roomId);
        Member member = memberRepository.getMemberById(memberId);

        member.dislike(room);
    }

    public void undislike(final Long memberId, final Long roomId) {
        Room room = findRoom(roomId);
        Member member = memberRepository.getMemberById(memberId);

        member.undislike(room);
    }
}
