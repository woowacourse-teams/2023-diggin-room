package com.digginroom.digginroom.repository;

import com.digginroom.digginroom.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
