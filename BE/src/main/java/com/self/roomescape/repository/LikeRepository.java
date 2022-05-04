package com.self.roomescape.repository;

import com.self.roomescape.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<List<Like>> findByUserUid(long uid);
}
