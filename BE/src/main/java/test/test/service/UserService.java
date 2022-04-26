package test.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.test.entity.User;
import test.test.repository.UserRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public void saveUser(User user){
        userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public User findUserByEmail(User user){
        return userRepository.findUserByEmail(user);
    }

    @Transactional(readOnly = false)
    public User findUserById(long id) throws Exception{
        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
        return user;
    }

}
