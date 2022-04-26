package test.test.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import test.test.entity.User;
import test.test.service.UserService;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String userPk){
        User user =userService.findUserById(Long.valueOf(userPk));
        return (UserDetails) user;
    }
}