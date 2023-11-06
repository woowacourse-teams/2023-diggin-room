package com.digginroom.digginroom.service;

import com.digginroom.digginroom.domain.Genre;
import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.domain.recommend.RoomRecommender;
import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.exception.RecommendException;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.repository.RoomRepository;
import com.digginroom.digginroom.service.dto.RoomResponse;
import com.digginroom.digginroom.service.dto.RoomsResponse;
import com.digginroom.digginroom.service.dto.TrackResponse;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import com.digginroom.digginroom.service.dto.RoomResponse;
import com.digginroom.digginroom.service.dto.RoomsResponse;
import com.digginroom.digginroom.service.dto.TrackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final RoomRecommender roomRecommender;
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
        } catch (RecommendException e) {
            return this.recommend(memberId);
        }
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
        Room room = roomRepository.getRoomById(roomId);
        Member member = memberRepository.getMemberById(memberId);
        member.scrap(room);
    }

    public void unscrap(final Long memberId, final Long roomId) {
        Room room = roomRepository.getRoomById(roomId);
        Member member = memberRepository.getMemberById(memberId);
        member.unscrap(room);
    }

    public void dislike(final Long memberId, final Long roomId) {
        Room room = roomRepository.getRoomById(roomId);
        Member member = memberRepository.getMemberById(memberId);

        member.dislike(room);
    }

    public void undislike(final Long memberId, final Long roomId) {
        Room room = roomRepository.getRoomById(roomId);
        Member member = memberRepository.getMemberById(memberId);

        member.undislike(room);
    }

    public CommentsResponse findRoomComments(final Long roomId, final Long loginMemberId) {
        validateExistRoom(roomId);
        Member member = memberService.findMember(loginMemberId);
        return commentService.getRoomComments(roomId, member);
    }

    public void validateExistRoom(final Long roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new NotFoundException(roomId);
        }
    }

    public CommentResponse comment(final Long roomId, final Long memberId, final CommentRequest request) {
        validateExistRoom(roomId);
        Member member = memberService.findMember(memberId);
        return commentService.comment(roomId, member, request);
    }

    public void deleteComment(final Long roomId, final Long memberId, final Long commentId) {
        validateExistRoom(roomId);
        Member member = memberService.findMember(memberId);
        commentService.delete(roomId, member, commentId);
    }

    public CommentResponse updateComment(
            final Long roomId,
            final Long memberId,
            final Long commentId,
            final CommentRequest request
    ) {
        validateExistRoom(roomId);
        Member member = memberService.findMember(memberId);
        Comment updateComment = commentService.update(member, roomId, commentId, request);
        return CommentResponse.of(updateComment, updateComment.isOwner(member));
    }
}
