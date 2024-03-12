package com.digginroom.digginroom.repository;

import com.digginroom.digginroom.domain.member.Member;
import com.digginroom.digginroom.exception.MemberException.NotFoundException;
import com.digginroom.digginroom.repository.dto.MemberNickname;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUsername(final String username);

    Optional<Member> findMemberByUsername(final String username);

    default Member getMemberByUsername(final String username) {
        return findMemberByUsername(username).orElseThrow(NotFoundException::new);
    }

    default Member getMemberById(final Long id) {
        return findById(id).orElseThrow(NotFoundException::new);
    }

    @Query("SELECT new com.digginroom.digginroom.repository.dto.MemberNickname(id, nickname.nickname) "
            + "FROM Member "
            + "WHERE id = :id ")
    Optional<MemberNickname> findMemberNicknameById(@Param("id") final Long id);

    default MemberNickname getMemberNickname(final Long id) {
        return findMemberNicknameById(id).orElseThrow(NotFoundException::new);
    }
}
