package com.itheima.reggie.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//web相关配置
@Configuration
public class ReggieWebMvcConfig implements WebMvcConfigurer {
    //设置静态资源映射
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //当访问请求是/backend/**时,去classpath:/backend/寻找对应资源
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
    }
}