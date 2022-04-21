package com.self.roomescape.controller;

import com.self.roomescape.service.KakaoAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private KakaoAPI kakaoAPI;

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestHeader Map<String,String> requestHeader) {
//        logger.info(code);
//        String access_Token = kakaoAPI.getAccessToken(code); // 여기까지가 액세스 토큰 받는 부분
        logger.info("read header : "+requestHeader);
        logger.info("controller access_token : " + requestHeader.get("token"));
        //토큰을 이용해 정보 받아오기
        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(requestHeader.get("token"));
        System.out.println("login Controller : " + userInfo);

//        if(userInfo.get("email")!=null){
//            System.out.println("있네");
//        }
        if( !(boolean)userInfo.get("resCode") ){
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestParam("a_token") String a_token) {
        kakaoAPI.kakaoLogout(a_token);

        return new ResponseEntity<>("로그아웃 성공", HttpStatus.OK);
    }

    @RequestMapping(value = "/unlink")
    public ResponseEntity<?> unlink(@RequestParam("a_token") String a_token){
        kakaoAPI.kakaoUnLink(a_token);

        return new ResponseEntity<>("연결 끊기 성공",HttpStatus.OK);
    }
}
