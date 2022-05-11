package com.self.roomescape.controller;

import com.self.roomescape.config.JwtTokenProvider;
import com.self.roomescape.entity.*;
import com.self.roomescape.repository.*;
import com.self.roomescape.request.RecruitCreateRequest;
import com.self.roomescape.request.ReviewCreateRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recruit")
@Api(tags = {"방탈출 인원 모집"})
public class RecruitController {

    private final CafeRepository cafeRepository;

    private final ThemeRepository themeRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    private final UserLikeRepository userLikeRepository;

    private final ReviewRepository reviewRepository;

    private final RecruitRepository recruitRepository;


    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "모집 글 쓰기", notes = "모집 글 쓰기")
    @PostMapping("/")
    public ResponseEntity<?> createRecruit(@Valid RecruitCreateRequest recruitCreateRequest, HttpServletRequest request) {

        String token = jwtTokenProvider.resolveToken(request);
        String useremail = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findUserByEmail(useremail);
        Recruit recruit = new Recruit();
        recruit.setUser(user);
        recruit.setTitle(recruitCreateRequest.getTitle());
        recruit.setContent(recruitCreateRequest.getContent());
        recruitRepository.save(recruit);
        if (recruit.getRid() != 0) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "모집 글 수정", notes = "모집 글 수정")
    @PostMapping("/{rid}")
    public ResponseEntity<?> updateRecruit(@Valid RecruitCreateRequest recruitCreateRequest, @PathVariable long rid , HttpServletRequest request) {
        Optional<Recruit> recruit = recruitRepository.findById(rid);
        if(recruit.isPresent()){
            String token = jwtTokenProvider.resolveToken(request);
            String useremail = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(useremail);
            if(recruit.get().getUser().getEmail().equals(user.getEmail())){
                recruit.get().setContent(recruitCreateRequest.getContent());
                recruit.get().setTitle(recruitCreateRequest.getTitle());
                recruitRepository.save(recruit.get());
                return new ResponseEntity<>(true, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "모집 글 삭제", notes = "모집 글 삭제")
    @DeleteMapping("/{rid}")
    public ResponseEntity<?> deleteRecruit(@PathVariable long rid ,HttpServletRequest request) {
        Optional<Recruit> recruit = recruitRepository.findById(rid);
        if(recruit.isPresent()){
            String token = jwtTokenProvider.resolveToken(request);
            String useremail = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(useremail);
            if(recruit.get().getUser().getEmail().equals(user.getEmail())){
                recruitRepository.delete(recruit.get());
                return new ResponseEntity<>(true, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "모집 글 읽기", notes = "모집 글 읽기")
    @GetMapping("/{rid}")
    public ResponseEntity<?> getRecruit(@PathVariable long rid) {
        Optional<Recruit> recruit = recruitRepository.findById(rid);
        if(recruit.isPresent()){
            return new ResponseEntity<>(recruit.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "모집 글 리스트 읽기", notes = "모집 글 리스트 읽기")
    @GetMapping("/")
    public ResponseEntity<?> getRecruitList() {
        List<Recruit> recruitList = recruitRepository.findAll();
        return new ResponseEntity<>(recruitList, HttpStatus.OK);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "작성일 기준 5개", notes = "작성일 기준 5개")
    @GetMapping("/new")
    public ResponseEntity<?> getRecruit5() {
        List<Recruit> recruitList = recruitRepository.findTop5ByOrderByCreateDateDesc();
        return new ResponseEntity<>(recruitList, HttpStatus.OK);
    }

}
