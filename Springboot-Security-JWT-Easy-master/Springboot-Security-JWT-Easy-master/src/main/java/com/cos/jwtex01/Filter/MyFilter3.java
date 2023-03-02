package com.cos.jwtex01.Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//토큰: cos 이걸 만들어줘야함. id/ pw 정상적으로 들어와서 로그인이 완료되면 토큰을 만들어주고 그걸 응답해준다.
//요청할때마다 header에 Authorization에 value 값으로 토큰을 가지고 온다.
//그때 토큰이 넘어오면 이 토큰이 내가만든 토큰이 맞는지만 검증하면 됨.(RSA, HS256)
public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("필터3");
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse) response;

        //토큰 : 코스 으로 가정.
        if(req.getMethod().equals("POST")){
            String headerAuth = req.getHeader("Authorization");
            System.out.println(headerAuth);

            if(headerAuth.equals("cos")){
                chain.doFilter(req,res);

            }else{
                PrintWriter outPrintWriter = res.getWriter();
                outPrintWriter.println("인증 안됨");
            }
        }
    }
}
