package com.cos.jwt.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig{

    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //내서버가 응답할때 json을 자바스크립트에서 처리가능하게 할지를 선택
        config.addAllowedHeader("*"); //모든 ip의 응답을 허용한다.
        config.addAllowedMethod("*"); //모든 header의 응답을 허용한다.
        config.addAllowedOrigin("*"); //모든 post/get/put/delete 요청을 허
        source.registerCorsConfiguration("/api/**",config);
        return new CorsFilter(source);
    }
}
