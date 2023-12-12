package com.digginroom.digginroom.service;

import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.domain.recommend.RoomRecommender;
import com.digginroom.digginroom.domain.room.Room;
import com.digginroom.digginroom.exception.RecommendException;
import com.digginroom.digginroom.membergenre.domain.vo.WeightFactor;
import com.digginroom.digginroom.repository.MemberRepository;
import com.digginroom.digginroom.repository.RoomRepository;
import com.digginroom.digginroom.service.dto.RoomResponse;
import com.digginroom.digginroom.service.dto.RoomsResponse;
import com.digginroom.digginroom.service.dto.TrackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final RoomRecommender roomRecommender;
    private final ApplicationEventPublisher publisher;

    @Transactional(readOnly = true)
    public RoomResponse recommend(final Long memberId) {
        Member member = memberRepository.getMemberById(memberId);

        try {
            Room recommendedRoom = roomRepository.getRoomById(roomRecommender.recommend(memberId));

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
        publisher.publishEvent(new DefaultMemberGenreEvent(
                room.getTrack().getSuperGenre(),
                memberId,
                WeightFactor.SCRAP
        ));
    }


    public void unscrap(final Long memberId, final Long roomId) {
        Room room = roomRepository.getRoomById(roomId);
        Member member = memberRepository.getMemberById(memberId);
        member.unscrap(room);
        publisher.publishEvent(new DefaultMemberGenreEvent(
                room.getTrack().getSuperGenre(),
                memberId,
                WeightFactor.UNSCRAP
        ));
    }

    public void dislike(final Long memberId, final Long roomId) {
        Room room = roomRepository.getRoomById(roomId);
        publisher.publishEvent(new DefaultMemberGenreEvent(
                room.getTrack().getSuperGenre(),
                memberId,
                WeightFactor.DISLIKE
        ));
    }

    public void undislike(final Long memberId, final Long roomId) {
        Room room = roomRepository.getRoomById(roomId);
        publisher.publishEvent(new DefaultMemberGenreEvent(
                room.getTrack().getSuperGenre(),
                memberId,
                WeightFactor.UNDISLIKE
        ));
    }
}
