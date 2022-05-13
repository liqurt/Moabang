package com.self.roomescape.repository;

import com.self.roomescape.entity.Community;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findTop5ByOrderByCreateDateDesc();
    List<Community> findAllByOrderByCreateDateDesc();
    List<Community> findAllByUser_Uid(long uid);
}
