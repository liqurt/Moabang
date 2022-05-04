package com.self.roomescape.repository;

import com.self.roomescape.entity.User;
import com.self.roomescape.entity.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, Long>, UserLikeRepoCommon{

}
