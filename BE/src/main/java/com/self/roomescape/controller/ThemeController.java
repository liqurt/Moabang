package com.self.roomescape.controller;

import com.self.roomescape.config.JwtTokenProvider;
import com.self.roomescape.entity.Cafe;
import com.self.roomescape.entity.Theme;
import com.self.roomescape.entity.User;
import com.self.roomescape.entity.UserLike;
import com.self.roomescape.repository.CafeRepository;
import com.self.roomescape.repository.ThemeRepository;
import com.self.roomescape.repository.UserLikeRepository;
import com.self.roomescape.repository.UserRepository;
import com.self.roomescape.repository.mapping.ThemeListMapping;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/theme")
public class ThemeController {
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserLikeRepository userLikeRepository;

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "테마 좋아요", notes = "좋아요 , 좋아요 취소 둘다 가능~")
    @GetMapping("/{themeid}/like")
    public ResponseEntity<?> findList(@PathVariable int themeid, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String useremail = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findUserByEmail(useremail);
        Theme theme = themeRepository.findThemesByTid(themeid);
        Optional<UserLike> userLike = userLikeRepository.findUserLikeByUserAndTheme(user,theme);
        if(userLike.isPresent()){
            userLikeRepository.delete(userLike.get());
            return new ResponseEntity<>("좋아요 취소 success", HttpStatus.OK);
        }else {
            if (user != null && theme != null) {
                UserLike addUserLike = UserLike.builder()
                        .theme(theme)
                        .user(user)
                        .build();
                userLikeRepository.save(addUserLike);
                return new ResponseEntity<>("좋아요 success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("fail", HttpStatus.OK);
            }
        }
    }


}
