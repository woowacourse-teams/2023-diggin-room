package com.digginroom.digginroom.repository;

import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.exception.MemberException.NotFoundException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUsername(final String username);

    Optional<Member> findMemberByUsername(final String username);

    default Member getMemberByUsername(final String username) {
        return findMemberByUsername(username).orElseThrow(NotFoundException::new);
    }

    default Member getMemberById(final Long id) {
        return findById(id).orElseThrow(NotFoundException::new);
    }
}
