package com.digginroom.digginroom.repository;

import com.digginroom.digginroom.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUsername(final String username);
}
