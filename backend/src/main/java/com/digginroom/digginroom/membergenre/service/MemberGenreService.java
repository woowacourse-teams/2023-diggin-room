package com.digginroom.digginroom.membergenre.service;

import com.digginroom.digginroom.exception.MemberException.FavoriteExistsException;
import com.digginroom.digginroom.membergenre.domain.MemberGenre;
import com.digginroom.digginroom.membergenre.domain.MemberGenreEvent;
import com.digginroom.digginroom.membergenre.domain.MemberGenreRepository;
import com.digginroom.digginroom.membergenre.domain.MemberGenres;
import com.digginroom.digginroom.service.dto.FavoriteGenresRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@Transactional
@AllArgsConstructor
public class MemberGenreService {

    private final MemberGenreRepository memberGenreRepository;

    public void markFavorite(final Long memberId, final FavoriteGenresRequest genres) {
        if (hasFavorite(memberId)) {
            throw new FavoriteExistsException();
        }
        MemberGenres memberGenres = MemberGenres.createMemberGenres(memberId);
        memberGenres.markFavorites(genres.favoriteGenres());
        memberGenreRepository.saveAll(memberGenres);
    }

    public List<MemberGenre> findMemberGenres(Long memberId) {
        return memberGenreRepository.findByMemberId(memberId);
    }

    public boolean hasFavorite(Long memberId) {
        return memberGenreRepository.existsByMemberId(memberId);
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void adjustMemberGenreWeight(final MemberGenreEvent memberGenreEvent) {
        MemberGenres memberGenres = toMemberGenres(memberGenreEvent.getMemberId());
        memberGenres.adjustWeight(memberGenreEvent.getGenre(), memberGenreEvent.getWeightFactor());
    }

    private MemberGenres toMemberGenres(final Long memberId) {
        return new MemberGenres(memberGenreRepository.findByMemberId(memberId));
    }
}
