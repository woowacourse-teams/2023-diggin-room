package com.digginroom.digginroom.membergenre.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberGenreRepository extends JpaRepository<MemberGenre, Long> {

    List<MemberGenre> findByMemberId(final Long memberId);

    boolean existsByMemberId(Long memberId);
}
