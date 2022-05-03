package com.self.roomescape.controller;

import com.self.roomescape.entity.Cafe;
import com.self.roomescape.entity.Theme;
import com.self.roomescape.repository.CafeRepository;
import com.self.roomescape.repository.ThemeRepository;
import com.self.roomescape.repository.mapping.ThemeListMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cafe")
public class CafeController {
    @Autowired
    private CafeRepository cafeRepository;
    @Autowired
    private ThemeRepository themeRepository;

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

    @GetMapping("/theme/listtest")
    public ResponseEntity<?> find2AllTheme() {
        List<ThemeListMapping> themeAllList = themeRepository.findThemeAndCafe();

        return new ResponseEntity<>(themeAllList, HttpStatus.OK);
    }
}
