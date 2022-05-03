package com.self.roomescape.controller;

import com.self.roomescape.entity.Cafe;
import com.self.roomescape.entity.Review;
import com.self.roomescape.entity.Theme;
import com.self.roomescape.repository.CafeRepository;
import com.self.roomescape.repository.ReviewRepository;
import com.self.roomescape.repository.ThemeRepository;
import com.self.roomescape.repository.mapping.ThemeListMapping;
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

    @GetMapping("/list")
    public ResponseEntity<?> findList() {
        List<Cafe> cafeList = cafeRepository.findAll();


        return new ResponseEntity<>(cafeList, HttpStatus.OK);
    }

    @GetMapping("/theme/{cid}")
    public ResponseEntity<?> findCafeTheme(@PathVariable("cid") int cid) {
        List<Theme> themeList = themeRepository.findByCid(cid);

        return new ResponseEntity<>(themeList, HttpStatus.OK);
    }

    @GetMapping("/theme/list")
    public ResponseEntity<?> findAllTheme() {
        List<ThemeListMapping> themeAllList = themeRepository.findThemeAndCafe();

        return new ResponseEntity<>(themeAllList, HttpStatus.OK);
    }

    @GetMapping("/theme/detail/{tid}")
    public ResponseEntity<?> findThemeDetail(@PathVariable int tid) {

        ThemeDetailResponse themeDetailResponse = new ThemeDetailResponse();
        Optional<Theme> themeInfo = themeRepository.findByTid(tid);

        if (!themeInfo.isPresent()) {
            return new ResponseEntity<>("tid : " + tid + "에 해당되는 정보가 없음", HttpStatus.OK);
        }

        themeDetailResponse.setTheme(themeInfo.get());

        //리뷰로 받으면 안된다... 유저 데이터 추가 해야함. 했음.
        Optional<List<Review>> rlist = reviewRepository.findByTid(tid);

        if (!rlist.isPresent()) {
            return new ResponseEntity<>(themeDetailResponse, HttpStatus.OK);
        }

        themeDetailResponse.setReviewList(rlist.get());


        return new ResponseEntity<>(themeDetailResponse, HttpStatus.OK);
    }
}
