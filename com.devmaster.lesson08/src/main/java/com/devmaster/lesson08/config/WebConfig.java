package com.devmaster.lesson08.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Map URL /images/** -> folder static/images/ trong project
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");

        // Nếu bạn lưu trong static/images/products
        registry.addResourceHandler("/images/products/**")
                .addResourceLocations("classpath:/static/images/products/");
    }
}
