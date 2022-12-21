package com.cos.jwt.config.jwt;

import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.vo.UserVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
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

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login 요청이 오면 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("attemptAuthentication 메서드 호출");

        System.out.println("=======================");
        try {
//            BufferedReader reader = request.getReader();
//            String input = null;
//            while ((input = reader.readLine()) != null){
//                System.out.println(input);
//            } 훨씬 쉬운방법이 있다
            ObjectMapper ob = new ObjectMapper();
            UserVo vo = ob.readValue(request.getInputStream(),UserVo.class);
            UsernamePasswordAuthenticationToken token
                    = new UsernamePasswordAuthenticationToken(vo.getUsername(),vo.getPassword());

            //authenticationManager.authenticate(token) 이 실행될때
            //PrincipalDetailsService 에 loadUserByUsername() 가 실행된다
            //정상이면 authentication 객체를 리턴함
            //DB에 있는 usernama 과 password가 일치한다
            Authentication authenticate = authenticationManager.authenticate(token);
            //=> 로그인이 되었다는것
            PrincipalDetails principal = (PrincipalDetails) authenticate.getPrincipal();
            System.out.println("principal.getUser().getUsername() = " + principal.getUser().getUsername());
            //authenticate 객체가 session 영역에 저장해야하고 그방법이 return해주면 됨
            //굳이 session 에 저장하는 이유는 권한관리를 Security가 해주기 때문에
            //JWT를 사용하면 session 을 사용할 이유가 없음 그치만 권한처리 때문에 세션을 사용함

            //JWT토큰을 만듦
            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //attemptAuthentication 인증이 완료가되면 successfulAuthentication 메서드에서 JWT토큰을 만들어주면 됨
    //JWT토큰을 만들어서 request를 요청한 사용자에게 response 해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("인증이 완료되었다는 뜻임");
        super.successfulAuthentication(request, response, chain, authResult);
    }

}
