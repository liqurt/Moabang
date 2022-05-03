package com.self.roomescape.repository;

import com.self.roomescape.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    List<Cafe> findAll();
}
