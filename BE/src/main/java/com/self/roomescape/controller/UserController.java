package com.self.roomescape.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.self.roomescape.entity.User;
import com.self.roomescape.repository.UserRepository;
import com.self.roomescape.service.KakaoAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private KakaoAPI kakaoAPI;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.secret}")
    private String key;

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //@RequestHeader Map<String,String> requestHeader
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestHeader Map<String, String> requestHeader, HttpServletResponse response) {
//        logger.info(code);
//        String access_Token = kakaoAPI.getAccessToken(code); // 여기까지가 액세스 토큰 받는 부분
//        logger.info("read header : " + access_Token);
//        logger.info("controller access_token : " + access_Token);
        logger.info("read header : " + requestHeader);
        logger.info("controller access_token : " + requestHeader.get("token"));
        //토큰을 이용해 정보 받아오기
//        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(access_Token);
        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(requestHeader.get("token"));
        System.out.println("login Controller : " + userInfo);

        if (!(boolean) userInfo.get("resCode")) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        Optional<User> isUser = userRepository.findByEmail((String) userInfo.get("email"));
        if (!isUser.isPresent()) { // 이메일 중복 확인.

//        userInfo.get("profile_img")  이미지 경로
            User user = User.builder()
                    .birthday((String) userInfo.get("birthday"))
                    .nickname((String) userInfo.get("nickname"))
                    .gender((String) userInfo.get("gender"))
                    .password(bCryptPasswordEncoder.encode("소셜로그인"))
                    .role("ROLE_USER")
                    .email((String) userInfo.get("email"))
                    .build();
            userRepository.save(user);
        }

        // 로그인 처리(토큰 발급)

        String jwtToken = JWT.create()
                .withSubject("JwtToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 10 * 6 * 10))
                .withClaim("email", (String) userInfo.get("email"))
                .sign(Algorithm.HMAC512(key));

        response.addHeader("Authorization", "Bearer " + jwtToken);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestParam("a_token") String a_token) {
        kakaoAPI.kakaoLogout(a_token);

        return new ResponseEntity<>("로그아웃 성공", HttpStatus.OK);
    }

    @RequestMapping(value = "/unlink")
    public ResponseEntity<?> unlink(@RequestParam("a_token") String a_token) {
        kakaoAPI.kakaoUnLink(a_token);

        return new ResponseEntity<>("연결 끊기 성공", HttpStatus.OK);
    }
}
