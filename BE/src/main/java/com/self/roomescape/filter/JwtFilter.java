package com.self.roomescape.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

//        if (req.getMethod().equals("POST")) {
//
//            String headerAuth = req.getHeader("Authorization");
//        System.out.println(headerAuth);
//
//        //id 정상적 들어와서 로그인이 되면 토큰을 만들어주고 그걸 응답 해줌.
//        //요청할 때 마다 header에 Authorization에 value값 토큰 가짐.
//        // 해당 토큰이 내가 만든 토큰이 맞는지 검증.( RSA, HS256)
//        if (headerAuth.equals("JwtToken")) {
//            chain.doFilter(req, res);
//        } else {
//            PrintWriter out = res.getWriter();
//            out.println("인증안됨");
//        }
//        }
        chain.doFilter(req, res);
    }
}
