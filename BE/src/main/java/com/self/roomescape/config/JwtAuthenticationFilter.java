package com.self.roomescape.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.self.roomescape.config.auth.PrincipalDetails;
import com.self.roomescape.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.secret}")
    private String key;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter : 로그인 시도중");

        try {
//            BufferedReader br = request.getReader();
//
//            String input = null;
//            while ((input = br.readLine()) != null) {
//                System.out.println(input);
//            }
//            System.out.println(request.getInputStream().toString());


            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

            // PrincipalDetailsService의 loadUserByUsername() 함수가 실행된 후 정상이면 authentication이 리턴.
            //DB에 있는 userEmail과 password가 일치하면
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 로그인이 되었다면.
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getUsername());

            // authentication 객첵가 session영역에 저장됨 그 방법이 return임.
            // return의 이유는 권한 관리를 security가 대신 해주기 때문에.
            // JWT 토큰을 사용하면서 세션을 사용할 이유가 없지만 권한 처리를 위해 session에 넣어 줌.

            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }

        //오류시 null 리턴
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String jwtToken = JWT.create()
                .withSubject("jwtToken")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 10 * 6 * 10))
                .withClaim("email", principalDetails.getUsername())
                .sign(Algorithm.HMAC512(key));

        response.addHeader("Authorization", "Bearer " + jwtToken);

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
