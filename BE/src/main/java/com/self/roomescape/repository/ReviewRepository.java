package com.self.roomescape.repository;

import com.self.roomescape.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<List<Review>> findByTid(int tid);

    Optional<Review> findByRid(long rid);

    void deleteByRid(long rid);
}
