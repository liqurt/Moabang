package com.self.roomescape.controller;

import com.self.roomescape.entity.Cafe;
import com.self.roomescape.entity.Review;
import com.self.roomescape.entity.Theme;
import com.self.roomescape.repository.CafeRepository;
import com.self.roomescape.repository.ReviewRepository;
import com.self.roomescape.repository.ThemeRepository;
import com.self.roomescape.repository.mapping.ThemeListMapping;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.ThemeDetailResponse;

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

    @ApiOperation(value = "카페 전체 목록", notes = "카페 전체 목록을 반환.")
    @GetMapping("/list")
    public ResponseEntity<?> findList() {
        List<Cafe> cafeList = cafeRepository.findAll();


        return new ResponseEntity<>(cafeList, HttpStatus.OK);
    }

    @ApiOperation(value = "테마 1개 검색", notes = "해당 tid에 해당하는 테마 정보를 반환")
    @GetMapping("/theme/{cid}")
    public ResponseEntity<?> findCafeTheme(@PathVariable("cid") int cid) {
        List<Theme> themeList = themeRepository.findByCid(cid);

        return new ResponseEntity<>(themeList, HttpStatus.OK);
    }

    @ApiOperation(value = "테마 전체 목록", notes = "테마 전체 목록을 카페 이름 및 URL과 같이 반환")
    @GetMapping("/theme/list")
    public ResponseEntity<?> findAllTheme() {
        List<ThemeListMapping> themeAllList = themeRepository.findThemeAndCafe();

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
}
