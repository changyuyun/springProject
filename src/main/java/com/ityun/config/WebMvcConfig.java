package com.ityun.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private SiteConfig siteConfig;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 设置自定义的目录为资源存放目录
        String location = "file:///" + siteConfig.getLocation();
        registry.addResourceHandler("/storage/avatars/**").addResourceLocations(location + "/storage/avatars/");
        registry.addResourceHandler("/storage/logs/**").addResourceLocations(location + "/storage/logs/");
    }
}
