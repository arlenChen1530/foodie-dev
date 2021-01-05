package com.arlenchen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author arlenchen
 */
@Configuration
public class CorsConfig  {
    public CorsConfig() {
    }

    @Bean
    public CorsFilter corsFilter(){
        //1.添加cors配置信息
        CorsConfiguration  configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://127.0.0.1:8080");
        configuration.addAllowedOrigin("*");
        //设置是否发送cookie信息
        configuration.setAllowCredentials(true);
        //设置允许请求的方式
        configuration.addAllowedMethod("*");
        //设置允许的header
        configuration.addAllowedHeader("*");

        //2.为url添加映射路径
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",configuration);

        //3. 返回重新定义好的cors source
         return  new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
