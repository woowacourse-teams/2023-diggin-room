package com.digginroom.digginroom.service;

import com.digginroom.digginroom.controller.dto.RoomResponse;
import com.digginroom.digginroom.controller.dto.ScrappedRoomResponse;
import com.digginroom.digginroom.controller.dto.ScrappedRoomsResponse;
import com.digginroom.digginroom.controller.dto.TrackResponse;
import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.domain.MemberGenres;
import com.digginroom.digginroom.domain.Room;
import com.digginroom.digginroom.domain.ScrapRooms;
import com.digginroom.digginroom.domain.Track;
import com.digginroom.digginroom.exception.RoomException.NotFoundException;
import com.digginroom.digginroom.repository.RoomRepository;
import com.digginroom.digginroom.repository.TrackRepository;
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
    private final TrackRepository trackRepository;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public RoomResponse recommend(final Long memberId) {
        Member member = memberService.findMember(memberId);
        MemberGenres memberGenres = member.getMemberGenres();
        try {
            Genre recommendedGenre = new GenreRecommender().recommend(memberGenres.getMemberGenres());
            Track recommendedTrack = recommendTrack(recommendedGenre);
            Room recommendedRoom = recommendedTrack.recommendRoom();

            return new RoomResponse(
                    recommendedRoom.getId(),
                    recommendedRoom.getMediaSource().getIdentifier(),
                    member.hasScrapped(recommendedRoom),
                    TrackResponse.of(recommendedRoom.getTrack())
            );
        } catch (IllegalArgumentException e) {
            return this.recommend(memberId);
        }
    }

    private Track recommendTrack(final Genre recommendedGenre) {
        List<Track> tracks = trackRepository.findBySuperGenre(recommendedGenre);
        int pickedIndex = ThreadLocalRandom.current().nextInt(tracks.size());

        return tracks.get(pickedIndex);
    }

    @Transactional(readOnly = true)
    public ScrappedRoomsResponse findScrappedRooms(final Long memberId) {
        Member member = memberService.findMember(memberId);
        ScrapRooms scrapRooms = member.getScrapRooms();
        return new ScrappedRoomsResponse(scrapRooms.getScrapRooms().stream()
                .map(room -> new ScrappedRoomResponse(room.getId(), room.getMediaSource().getIdentifier()))
                .toList());
    }

    public void scrap(final Long memberId, final Long roomId) {
        Room room = findRoom(roomId);
        Member member = memberService.findMember(memberId);

        member.scrap(room);
    }

    public void unscrap(final Long memberId, final Long roomId) {
        Room room = findRoom(roomId);
        Member member = memberService.findMember(memberId);

        member.unscrap(room);
    }

    private Room findRoom(final Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new NotFoundException(roomId));
    }

    public void dislike(final Long memberId, final Long roomId) {
        Room room = findRoom(roomId);
        Member member = memberService.findMember(memberId);

        member.dislike(room);
    }

    public void undislike(final Long memberId, final Long roomId) {
        Room room = findRoom(roomId);
        Member member = memberService.findMember(memberId);

        member.undislike(room);
    }
}
