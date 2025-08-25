package com.example.bytecookie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:5173",
                        "http://localhost:5174",
                        "https://seniornavi-frontend-three.vercel.app",
                        "https://seniornavi-frontend-it3cqxxjw-benscookies-projects.vercel.app",
                        "http://localhost:8080",
                        "https://api-bytecookie.click")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"
                )
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}

