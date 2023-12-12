package com.digginroom.digginroom.membergenre.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberGenreRepository extends JpaRepository<MemberGenre, Long> {

    default MemberGenres getAllByMemberId(final Long memberId) {
        return new MemberGenres(findByMemberId(memberId));
    }

    List<MemberGenre> findByMemberId(final Long memberId);

    boolean existsByMemberId(Long memberId);
}
