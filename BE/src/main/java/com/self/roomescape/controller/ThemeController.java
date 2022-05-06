package com.self.roomescape.controller;

import com.self.roomescape.config.JwtTokenProvider;
import com.self.roomescape.entity.*;
import com.self.roomescape.repository.*;
import com.self.roomescape.repository.mapping.ThemeListMapping;
import com.self.roomescape.request.ReviewCreateRequest;
import com.self.roomescape.request.ReviewUpdateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/theme")
@Api(tags = {"테마 및 리뷰 Api"})
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
    @Autowired
    private ReviewRepository reviewRepository;

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "테마 좋아요", notes = "좋아요 , 좋아요 취소 둘다 가능~")
    @GetMapping("/{themeid}/like")
    public ResponseEntity<?> findList(@PathVariable int themeid, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        String useremail = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findUserByEmail(useremail);
        Theme theme = themeRepository.findThemesByTid(themeid);
        Optional<UserLike> userLike = userLikeRepository.findUserLikeByUserAndTheme(user, theme);
        if (userLike.isPresent()) {
            userLikeRepository.delete(userLike.get());
            return new ResponseEntity<>("좋아요 취소 success", HttpStatus.OK);
        } else {
            if (user != null && theme != null) {
                UserLike addUserLike = UserLike.builder().theme(theme).user(user).build();
                userLikeRepository.save(addUserLike);
                return new ResponseEntity<>("좋아요 success", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("fail", HttpStatus.OK);
            }
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "해당 테마 리뷰 쓰기", notes = "email 빼고 nullable 및 playDate 입력시 `yyyy-MM-dd` 형태로 입력, 생성 시 HttpStatus 200 및 true 값 반환 실패 시 400 및 false 반환")
    @PostMapping("/review/create")
    public ResponseEntity<?> createReview(@RequestBody ReviewCreateRequest reviewCreateRequest, HttpServletRequest request) {
        // 리뷰 수 늘리는 거 추가 하고 평점 바뀐거 반영 해야함.
        Optional<Theme> theme = themeRepository.findByTid(reviewCreateRequest.getTid());


        if (theme.isPresent()) {

            String token = jwtTokenProvider.resolveToken(request);
            String useremail = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(useremail);

            Review review = new Review();
            review.setContent(reviewCreateRequest.getContent());
            review.setActive(reviewCreateRequest.getActive());
            review.setDifficulty(reviewCreateRequest.getDifficulty());
            review.setHint(reviewCreateRequest.getHint());
            review.setIsSuccess(reviewCreateRequest.getIsSuccess());
            review.setPlayer(reviewCreateRequest.getPlayer());
            review.setContent(reviewCreateRequest.getContent());
            review.setRating(reviewCreateRequest.getRating());
            review.setTid(reviewCreateRequest.getTid());
            review.setTimeLeft(reviewCreateRequest.getTimeLeft());
            review.setRecPlayer(reviewCreateRequest.getRecPlayer());
            //uid 랑 playdate 필요
            review.setPlayDate(reviewCreateRequest.getPlayDate());
            review.setUserInfo(UserInfo.builder().uid(user.getUid()).build());
            reviewRepository.save(review);

            Theme temp = theme.get();

            //평점 반영
            float totalGrade = temp.getGrade() * (float) temp.getReviewCnt(); // 평점의 총 평균 점수 ...-> a

            float sumGrade = totalGrade + reviewCreateRequest.getRating(); // a + 입력받은 평점 점수 의 합 ...-> b

            float resGrade = sumGrade / (temp.getReviewCnt() + 1); // b / 원본 리뷰 수 + 1;
            temp.setGrade((float) (Math.floor(resGrade * 100) / 100.00));

            //리뷰 수 증가
            temp.setReviewCnt(temp.getReviewCnt() + 1);

//            themeRepository.save(temp);

            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "해당 테마 리뷰 삭제", notes = "JWT 토큰으로 온 유저의 Uid가 해당 게시물의 Uid와 같으면 삭제 ,삭제 시 HttpStatus 200 및 true 값 반환 실패 시 400 및 false 반환")
    @Transactional
    @DeleteMapping("/review/delete/{rid}")
    public ResponseEntity<?> deleteReview(@PathVariable long rid, HttpServletRequest request) {

        Optional<Review> review = reviewRepository.findByRid(rid);
        if (!review.isPresent()) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        // 1. 해당 유저가 게시글 주인인지 확인
        String token = jwtTokenProvider.resolveToken(request);
        String useremail = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findUserByEmail(useremail);

        if (review.get().getUserInfo().getUid() != user.getUid()) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        // 2. 리뷰 수 줄이고 평점 반영.
        Optional<Theme> theme = themeRepository.findByTid(review.get().getTid());
        //평점 반영
        Theme temp = theme.get();
        float totalGrade = temp.getGrade() * (float) temp.getReviewCnt(); // 평점의 총 평균 점수 ...-> a

        System.out.println("원 점수 : " + temp.getGrade());
        float sumGrade = totalGrade - review.get().getRating(); // a - 입력받은 평점 점수 의 합 ...-> b

        float resGrade = sumGrade / (temp.getReviewCnt() - 1); // b / 원본 리뷰 수 - 1;
        temp.setGrade((float) (Math.floor(resGrade * 100) / 100.00));

        System.out.println("감소 평점 점수 : " + resGrade);

        //리뷰 수 감소
        temp.setReviewCnt(temp.getReviewCnt() - 1);

//        themeRepository.save(temp);

        reviewRepository.deleteByRid(rid);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "해당 테마 리뷰 수정", notes = "JWT 토큰으로 온 유저의 Uid가 해당 게시물의 Uid와 같으면 수정 \n" +
            "수정 시 못바꾸는 값은 현재 reviewid, uid, tid 3개 ,수정 시 HttpStatus 200 및 true 값 반환 실패 시 400 및 false 반환")
    @PutMapping("/review/update/{rid}")
    public ResponseEntity<?> updateReview(@RequestBody ReviewUpdateRequest reviewUpdateRequest, HttpServletRequest request) {
        Optional<Review> review = reviewRepository.findByRid(reviewUpdateRequest.getReview_id());
        if (!review.isPresent()) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        // 1. 해당 유저가 게시글 주인인지 확인
        String token = jwtTokenProvider.resolveToken(request);
        String useremail = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findUserByEmail(useremail);

        if (review.get().getUserInfo().getUid() != user.getUid()) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        // 2. 해당 유저가 입력한 값 그대로 가져감.
        Review temp = review.get();

        temp.setRating(reviewUpdateRequest.getRating());
        temp.setIsSuccess(reviewUpdateRequest.getIsSuccess());
        temp.setHint(reviewUpdateRequest.getHint());
        temp.setTimeLeft(reviewUpdateRequest.getTimeLeft());
        temp.setPlayer(reviewUpdateRequest.getPlayer());
        temp.setRecPlayer(reviewUpdateRequest.getRecPlayer());
        temp.setActive(reviewUpdateRequest.getActive());
        temp.setDifficulty(reviewUpdateRequest.getDifficulty());
        temp.setPlayDate(reviewUpdateRequest.getPlayDate());
        temp.setContent(reviewUpdateRequest.getContent());

        reviewRepository.save(temp);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @ApiOperation(value = "해당 테마 리뷰 읽기", notes = "테마 리뷰 읽기 성공 시 HttpStatus 200 및 리스트 목록 없을 시 200 및 false 반환")
    @GetMapping("/review/list/{tid}")
    public ResponseEntity<?> showReviewList(@PathVariable int tid) {
        Optional<List<Review>> rlist = reviewRepository.findByTid(tid);

        if (rlist.get().size() == 0) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(rlist.get(), HttpStatus.OK);
    }
}
