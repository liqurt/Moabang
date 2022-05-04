package com.self.roomescape.controller;

import com.self.roomescape.entity.*;
import com.self.roomescape.repository.*;
import com.self.roomescape.repository.mapping.ThemeListMapping;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import response.ThemeDetailResponse;
import response.ThemeListResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cafe")
public class CafeController {
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserLikeRepository userLikeRepository;
    @Value("${spring.jwt.secret}")
    private String secretKey;


    @ApiOperation(value = "카페 전체 목록", notes = "카페 전체 목록을 반환.")
    @GetMapping("/list")
    public ResponseEntity<?> findList() {
        List<Cafe> cafeList = cafeRepository.findAll();


        return new ResponseEntity<>(cafeList, HttpStatus.OK);
    }

    @ApiOperation(value = "해당 카페의 테마 검색", notes = "해당 tid에 해당하는 테마 정보를 반환")
    @GetMapping("/theme/{cid}")
    public ResponseEntity<?> findCafeTheme(@PathVariable("cid") int cid) {
        List<Theme> themeList = themeRepository.findByCid(cid);

        return new ResponseEntity<>(themeList, HttpStatus.OK);
    }

    @ApiOperation(value = "테마 전체 목록", notes = "테마 전체 목록을 카페 이름 및 URL과 같이 반환")
    @GetMapping("/theme/list")
    public ResponseEntity<?> findAllTheme(HttpServletRequest request) {

        List<ThemeListMapping> themeAllList = themeRepository.findThemeAndCafe();
        List<ThemeListResponse> themeListResponses = new ArrayList<>();

        if (request.getHeader("Authorization") != null) {
            String token = request.getHeader("Authorization").substring(7);
            Optional<User> user = userRepository.findByEmail(this.getUserPk(token));
            if (!user.isPresent()) {
                return new ResponseEntity<>("회원 정보가 없음", HttpStatus.BAD_REQUEST);
            }


            // user 좋아요 정보 불러오는 부분

            // logic 1. 얻은 uid를 통해서 like 테이블에 좋아요한 tid가 있는지 확인.
            Optional<List<UserLike>> likeList = userLikeRepository.findUserLikeByUser(user.get());

            if (likeList.isPresent()) {
                // 2. tid가 있다면 받아온 themeInfo에 isLike에 배열로 비교하면서 같다면 true 아니면 false를 집어넣는다..
                for (int i = 0; i < themeAllList.size(); i++) {
                    ThemeListResponse temp = new ThemeListResponse();
                    temp.setActivity(themeAllList.get(i).getActivity());
                    temp.setCid(themeAllList.get(i).getCid());
                    temp.setCname(themeAllList.get(i).getCname());
                    temp.setCurl(themeAllList.get(i).getCurl());
                    temp.setDifficulty(themeAllList.get(i).getDifficulty());
                    temp.setDescription(themeAllList.get(i).getDescription());
                    temp.setGenre(themeAllList.get(i).getGenre());
                    temp.setImg(themeAllList.get(i).getImg());
                    temp.setIsland(themeAllList.get(i).getIsland());
                    temp.setSi(themeAllList.get(i).getSi());
                    temp.setGrade(themeAllList.get(i).getGrade());
                    temp.setRplayer(themeAllList.get(i).getRplayer());
                    temp.setTid(themeAllList.get(i).getTid());
                    temp.setTime(themeAllList.get(i).getTime());
                    temp.setType(themeAllList.get(i).getType());
                    temp.setTname(themeAllList.get(i).getTname());

                    for (int j = 0; j < likeList.get().size(); j++) {
                        System.out.println("test : "+likeList.get().get(j).getTheme());
                        if (themeAllList.get(i).getTid() == likeList.get().get(j).getTheme().getTid()){
                            temp.setIslike(true);
                            ;// true 정보 넣기
                        } else {
                            temp.setIslike(false);
                            ;// false 정보 넣기
                        }
                    }
                    themeListResponses.add(temp);
                }
            }
            return new ResponseEntity<>(themeListResponses, HttpStatus.OK);
        }


        // 3. 아래 step 진행 그대로 하면 된다. 수정사항 없음.

        return new ResponseEntity<>(themeAllList, HttpStatus.OK);
    }

    @ApiOperation(value = "테마 상세 정보", notes = "해당 Tid를 통해서 테마 상세 정보 및 댓글 목록을 반환한다.")
    @GetMapping("/theme/detail/{tid}")
    public ResponseEntity<?> findThemeDetail(@PathVariable int tid) {

        ThemeDetailResponse themeDetailResponse = new ThemeDetailResponse();
        Optional<Theme> themeInfo = themeRepository.findByTid(tid);

        if (!themeInfo.isPresent()) {
            return new ResponseEntity<>("tid : " + tid + "에 해당되는 정보가 없음", HttpStatus.OK);
        }

        themeDetailResponse.setTheme(themeInfo.get());

        Optional<List<Review>> rlist = reviewRepository.findByTid(tid);

        if (!rlist.isPresent()) {
            return new ResponseEntity<>(themeDetailResponse, HttpStatus.OK);
        }

        themeDetailResponse.setReviewList(rlist.get());


        return new ResponseEntity<>(themeDetailResponse, HttpStatus.OK);
    }

    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }
}
