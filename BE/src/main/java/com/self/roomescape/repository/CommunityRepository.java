package com.self.roomescape.repository;

import com.self.roomescape.entity.Community;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    public List<Community> findTop5ByOrderByCreateDateDesc();

    public List<Community> findAllByOrderByCreateDateDesc();

    List<Community> findByUserUidOrderByCreateDate(long uid);
}
