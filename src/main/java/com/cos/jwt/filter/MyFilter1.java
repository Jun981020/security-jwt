package com.cos.jwt.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter1 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("안녕?");
        HttpServletRequest req =(HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if(req.getMethod().equals("POST")){
            String headerAuthor = req.getHeader("Authorization");
            System.out.println(headerAuthor);
            if(headerAuthor.equals("cos")){
                chain.doFilter(req,res);
                System.out.println("인증완료");
            }else{
                PrintWriter out = res.getWriter();
                out.println("인증안됨");
            }
        }
    }
}
