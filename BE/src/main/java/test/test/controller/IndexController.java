package test.test.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
//import test.test.config.auth.dto.SessionUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final HttpSession httpSession;
//    @GetMapping("/")
//    public String index(Model model) {
//
////        SessionUser user = (SessionUser) httpSession.getAttribute("user");
////
////        if(user != null){
////            model.addAttribute("userName", user.getName());
////        }
//
//        return "index";
//    }
    @GetMapping("/images/callback")
    public String mapRequest(@RequestBody HashMap<String, Object> param){
        System.out.println("hi");

        System.out.println("param : " + param);

        return param.toString();
    }
    @GetMapping(value = "/test/login" , produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String ho(HttpServletRequest request) throws IOException {
        System.out.println("hi");
        String token = resolveToken(request);
        System.out.println(token);
        final String gurl = "https://www.googleapis.com/oauth2/v2/userinfo" + "?access_token="+token;
        URL url = new URL(gurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String line = "";
        String result = "";

        while ((line = br.readLine()) != null) {
            result += line;
        }
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);
        String id = element.getAsJsonObject().get("id").getAsString();
        String email = element.getAsJsonObject().get("email").getAsString();
        String nickname = element.getAsJsonObject().get("name").getAsString();
        String profile_img = element.getAsJsonObject().get("picture").getAsString();
        return id+email+nickname+profile_img;

    }
    public String resolveToken(HttpServletRequest req){
        return req.getHeader("code");
    }
}
