package com.self.roomescape.repository;

import com.self.roomescape.entity.Recruit;
import com.self.roomescape.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecruitRepository extends JpaRepository<Recruit, Long> {

}
