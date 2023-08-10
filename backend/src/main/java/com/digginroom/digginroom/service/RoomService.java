package com.digginroom.digginroom.service;

import com.digginroom.digginroom.controller.dto.CommentRequest;
import com.digginroom.digginroom.controller.dto.CommentResponse;
import com.digginroom.digginroom.controller.dto.RoomResponse;
import com.digginroom.digginroom.controller.dto.RoomsResponse;
import com.digginroom.digginroom.controller.dto.TrackResponse;
import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.Member;
import com.digginroom.digginroom.domain.Room;
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
    private final CommentService commentService;

    @Transactional(readOnly = true)
    public RoomResponse recommend(final Long memberId) {
        Member member = memberService.findMember(memberId);

        try {
            Genre recommendedGenre = new GenreRecommender().recommend(member.getMemberGenres());
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
    public RoomsResponse findScrappedRooms(final Long memberId) {
        Member member = memberService.findMember(memberId);
        return new RoomsResponse(member.getScrapRooms().stream()
                .map(room -> new RoomResponse(
                        room.getId(),
                        room.getMediaSource().getIdentifier(),
                        member.hasScrapped(room),
                        TrackResponse.of(room.getTrack())
                ))
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

    public CommentResponse comment(final Long roomId, final Long memberId, final CommentRequest request) {
        Room room = findRoom(roomId);
        Member member = memberService.findMember(memberId);
        return commentService.comment(room, member, request);
    }
}
