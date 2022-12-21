package com.cos.jwt.controller;

import com.cos.jwt.model.User;
import com.cos.jwt.model.UserRepository;
import com.cos.jwt.vo.UserVo;
import lombok.RequiredArgsConstructor;
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
}
