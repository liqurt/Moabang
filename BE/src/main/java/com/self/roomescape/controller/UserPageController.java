package com.self.roomescape.controller;

import com.self.roomescape.config.JwtTokenProvider;
import com.self.roomescape.entity.User;
import com.self.roomescape.repository.ThemeRepository;
import com.self.roomescape.repository.UserRepository;
import com.self.roomescape.repository.mapping.ThemeListMapping;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import response.ThemeDetailResDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/page")
public class UserPageController {
    /*
    1.
    보낼거:  없음(헤더에 토큰만)
    받을거: 내가 좋아요 한 테마 리스트
    - 리턴값은 /cafe/theme/list랑 동일하게
    */
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ThemeRepository themeRepository;

    @GetMapping("/theme/list")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Authorization", required = false, dataType = "string", paramType = "header")})
    public ResponseEntity<?> UserLikeList(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String useremail = jwtTokenProvider.getUserPk(token);
        Optional<User> user = userRepository.findByEmail(useremail);
        if (!user.isPresent()) {
            return new ResponseEntity<>("해당 유저 없음", HttpStatus.BAD_REQUEST);
        }
        List<ThemeDetailResDTO> themeDetailResDTOList = new ArrayList<>();
        List<ThemeListMapping> themeList = themeRepository.findThemeFetch(user.get().getUid());
        for (int i = 0; i < themeList.size(); i++) {
            ThemeDetailResDTO tmp = new ThemeDetailResDTO();
            tmp.setActivity(themeList.get(i).getActivity());
            tmp.setCid(themeList.get(i).getCid());
            tmp.setCname(themeList.get(i).getCname());
            tmp.setUrl(themeList.get(i).getUrl());
            tmp.setDifficulty(themeList.get(i).getDifficulty());
            tmp.setDescription(themeList.get(i).getDescription());
            tmp.setGenre(themeList.get(i).getGenre());
            tmp.setImg(themeList.get(i).getImg());
            tmp.setIsland(themeList.get(i).getIsland());
            tmp.setSi(themeList.get(i).getSi());
            tmp.setGrade(themeList.get(i).getGrade());
            tmp.setRplayer(themeList.get(i).getRplayer());
            tmp.setTid(themeList.get(i).getTid());
            tmp.setTime(themeList.get(i).getTime());
            tmp.setType(themeList.get(i).getType());
            tmp.setTname(themeList.get(i).getTname());
            tmp.setCount(themeList.get(i).getCount());
            tmp.setIslike(true);

            String data = tmp.getRplayer();
            String[] srr = data.split("~");
            if (srr.length != 1) {
                tmp.setMin_player(Integer.parseInt(srr[0]));
                tmp.setMax_player(Integer.parseInt(srr[1]));
            } else {
                tmp.setMin_player(Integer.parseInt(srr[0]));
                tmp.setMax_player(Integer.parseInt(srr[0]));
            }
            themeDetailResDTOList.add(tmp);

        }
        return new ResponseEntity<>(themeDetailResDTOList, HttpStatus.OK);
    }

    /*
    2.
    보낼거: 없음(헤더에 토큰만)
    받을거: 내가 리뷰 남긴 테마의 tid 리스트
    */
    /*
    3.
    보낼거: 없음(헤더에 토큰만)
    받을거: 내가 리뷰 남긴 테마 리스트
- 리턴값은 /cafe/theme/list랑 동일하게
     */
}
