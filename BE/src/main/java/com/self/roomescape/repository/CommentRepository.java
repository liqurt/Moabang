package com.self.roomescape.repository;

import com.self.roomescape.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCommunityIdOrderByRegDateDesc(long community_id);

    Optional<Comment> findByCoid(long coid);
}
