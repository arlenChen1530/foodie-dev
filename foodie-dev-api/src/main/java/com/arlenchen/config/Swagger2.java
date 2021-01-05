package com.arlenchen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author arlenchen
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createDocketRestApi() {

        return //指定文档类型
                new Docket(DocumentationType.SWAGGER_2)
                        //用于定义api文档信息
                        .apiInfo(createApiInfo())
                        //指定controller包
                        .select().apis(RequestHandlerSelectors.basePackage("com.arlenchen.controller"))
                        //所以controller
                        .paths(PathSelectors.any())
                        .build();

    }

    private ApiInfo createApiInfo() {
        return
                //文档页标题
                new ApiInfoBuilder().title("电商平台接口api")
                        //联系人信息
                        .contact(new Contact("arlenChen", "1043905823@qq.com", "1043905823@qq.com"))
                        //详细信息
                        .description("专为电商平台提供的api文档")
                        //文档版本号
                        .version("1.0.1")
                        //网站地址
                        .termsOfServiceUrl("1043905823@qq.com")
                        .build();
    }
}
