package com.self.roomescape.controller;

import com.self.roomescape.config.JwtTokenProvider;
import com.self.roomescape.entity.*;
import com.self.roomescape.repository.*;
import com.self.roomescape.request.RecruitCreateRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/community")
@Api(tags = {"방탈출 인원 모집"})
public class CommunityController {

    private final CafeRepository cafeRepository;

    private final ThemeRepository themeRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    private final UserLikeRepository userLikeRepository;

    private final ReviewRepository reviewRepository;

    private final CommunityRepository communityRepository;


    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "글 쓰기", notes = "글 쓰기")
    @PostMapping("")
    public ResponseEntity<?> createRecruit(@Valid RecruitCreateRequest recruitCreateRequest, HttpServletRequest request) {

        String token = jwtTokenProvider.resolveToken(request);
        String useremail = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findUserByEmail(useremail);
        Community community = new Community();
        community.setUser(user);
        community.setTitle(recruitCreateRequest.getTitle());
        community.setContent(recruitCreateRequest.getContent());
        community.setHeader(recruitCreateRequest.getHeader());
        communityRepository.save(community);
        if (community.getRid() != 0) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "글 수정", notes = "글 수정")
    @PostMapping("/{rid}")
    public ResponseEntity<?> updateRecruit(@Valid RecruitCreateRequest recruitCreateRequest, @PathVariable long rid , HttpServletRequest request) {
        Optional<Community> recruit = communityRepository.findById(rid);
        if(recruit.isPresent()){
            String token = jwtTokenProvider.resolveToken(request);
            String useremail = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(useremail);
            if(recruit.get().getUser().getEmail().equals(user.getEmail())){
                recruit.get().setContent(recruitCreateRequest.getContent());
                recruit.get().setTitle(recruitCreateRequest.getTitle());
                recruit.get().setHeader(recruitCreateRequest.getHeader());
                communityRepository.save(recruit.get());
                return new ResponseEntity<>(true, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "글 삭제", notes = "글 삭제")
    @DeleteMapping("/{rid}")
    public ResponseEntity<?> deleteRecruit(@PathVariable long rid ,HttpServletRequest request) {
        Optional<Community> recruit = communityRepository.findById(rid);
        if(recruit.isPresent()){
            String token = jwtTokenProvider.resolveToken(request);
            String useremail = jwtTokenProvider.getUserPk(token);
            User user = userRepository.findUserByEmail(useremail);
            if(recruit.get().getUser().getEmail().equals(user.getEmail())){
                communityRepository.delete(recruit.get());
                return new ResponseEntity<>(true, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "글 읽기", notes = "글 읽기")
    @GetMapping("/{rid}")
    public ResponseEntity<?> getRecruit(@PathVariable long rid) {
        Optional<Community> recruit = communityRepository.findById(rid);
        if(recruit.isPresent()){
            return new ResponseEntity<>(recruit.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "글 리스트 읽기", notes = "글 리스트 읽기")
    @GetMapping("")
    public ResponseEntity<?> getRecruitList() {
        List<Community> communityList = communityRepository.findAllByOrderByCreateDateDesc();
        return new ResponseEntity<>(communityList, HttpStatus.OK);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "작성일 기준 5개", notes = "작성일 기준 5개")
    @GetMapping("/new")
    public ResponseEntity<?> getRecruit5() {
        List<Community> communityList = communityRepository.findTop5ByOrderByCreateDateDesc();
        return new ResponseEntity<>(communityList, HttpStatus.OK);
    }

}
