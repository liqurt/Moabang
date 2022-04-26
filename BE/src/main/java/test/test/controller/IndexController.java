package test.test.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import test.test.config.auth.JwtTokenProvider;
import test.test.entity.Role;
import test.test.entity.User;
import test.test.service.UserService;
//import test.test.config.auth.dto.SessionUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import javax.validation.Valid;


@RequiredArgsConstructor
@Controller
@RequestMapping(value ="")
@Api(tags = {"z"})
public class IndexController {
    private final UserService userService;
    private final HttpSession httpSession;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/bb")
    public String index(Model model) {

//        SessionUser user = (SessionUser) httpSession.getAttribute("user");
//
//        if(user != null){
//            model.addAttribute("userName", user.getName());
//        }

        return "index";
    }
//    @GetMapping("/images/callback")
//    public String mapRequest(@RequestBody HashMap<String, Object> param){
//        System.out.println("hi");
//
//        System.out.println("param : " + param);
//
//        return param.toString();
//    }
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "token넣어주세용", required = true, dataType = "string", paramType = "header")})
    @ApiOperation(value = "로긴", notes = "로긴")
//    @GetMapping(value = "/test/login" , produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/test/login")
    public @ResponseBody
    String ho(HttpServletRequest request) throws IOException {
        String token = resolveToken(request);
        final String gurl = "https://www.googleapis.com/oauth2/v2/userinfo" + "?access_token=" + token;
        URL url = new URL(gurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            String userid = element.getAsJsonObject().get("id").getAsString();
            String email = element.getAsJsonObject().get("email").getAsString();
            String nickname = element.getAsJsonObject().get("name").getAsString();
            String profile_img = element.getAsJsonObject().get("picture").getAsString();
            User user = User.builder()
                    .name(nickname)
                    .email(email)
                    .picture(profile_img)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
            User user1 = userService.findUserByEmail(user);
            List<String> list = Arrays.asList("ROLE_USER");
            if (user1 == null) {
                userService.saveUser(user);
                String jwttoken = jwtTokenProvider.createToken(String.valueOf(user.getUserid()), list);
                return jwttoken;
            } else {
                String jwttoken = jwtTokenProvider.createToken(String.valueOf(user1.getUserid()), list);
                return jwttoken;
            }


        } else {
            return "토큰이상함ㅅㄱ";
        }
    }
//    @GetMapping("/zz/z")
//    public @ResponseBody String zz(){
//        System.out.println("ASdf");
//        return "되나요";
//    }
//    @GetMapping("/sociallogin/google")
//    public @ResponseBody String zzz(HttpServletRequest request){
//        Map<String, String> requestHeaders = new HashMap<>();
//
//        return a;
//    }

    public String resolveToken(HttpServletRequest req){
        return req.getHeader("code");
    }
}
