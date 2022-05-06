package com.self.roomescape.repository;

import com.self.roomescape.entity.Theme;
import com.self.roomescape.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLikeRepoCommon {
    Boolean check(User user, Theme theme);
}
