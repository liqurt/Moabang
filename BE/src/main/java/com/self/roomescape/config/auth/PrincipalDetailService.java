package com.self.roomescape.config.auth;

import com.self.roomescape.entity.User;
import com.self.roomescape.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailService의 loadUserByUsername");
        User userEntity = userRepository.findByEmail(email).get();
//                .orElseThrow(()->new UsernameNotFoundException("not found email :" + email));
        return new PrincipalDetails(userEntity);
    }
}
