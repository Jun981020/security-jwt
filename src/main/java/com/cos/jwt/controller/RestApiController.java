package com.cos.jwt.controller;

import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.cos.jwt.model.UserRepository;
import com.cos.jwt.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RestApiController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @GetMapping("/home")
    public String home(){
        return "<h1>HOME</h1>";
    }
    @PostMapping("/token")
    public String token(){
        return "<h1>TOKEN</h1>";
    }
    @PostMapping("/join")
    public String join(@RequestBody UserVo vo){
        User user = User.createUser(vo.getUsername(), bCryptPasswordEncoder.encode(vo.getPassword()),"ROLE_USER");
        userRepository.save(user);
        return "회원가입 완료";
    }
    @GetMapping("/api/v1/user")
    public String user(Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principalDetails = " + principalDetails.getUsername());
        return "USER~";
    }
    @GetMapping("/api/v1/manager")
    public String manager(){
        return "MANAGER~";
    }
    @GetMapping("/api/v1/admin")
    public String admin(){
        return "ADMIN~";
    }
}
