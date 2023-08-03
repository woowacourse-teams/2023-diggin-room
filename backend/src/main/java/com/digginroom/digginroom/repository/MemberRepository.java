package com.digginroom.digginroom.repository;

import com.digginroom.digginroom.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUsername(final String username);

    Optional<Member> findMemberByUsername(final String username);
}
