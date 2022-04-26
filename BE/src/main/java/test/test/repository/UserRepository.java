package test.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.test.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepoCommon {

}
