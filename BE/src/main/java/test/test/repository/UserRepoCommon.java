package test.test.repository;

import org.springframework.stereotype.Repository;
import test.test.entity.User;

@Repository
public interface UserRepoCommon {
    User findUserByEmail(User user);
}
