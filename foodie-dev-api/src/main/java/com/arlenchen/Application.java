package com.arlenchen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//扫描mbatis通用mapper所在的包
@MapperScan(basePackages = "com.arlenchen.mapper")
//扫码所有包以及相关组件包
@ComponentScan(basePackages = {"org.n3r.idworker","com.arlenchen"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
