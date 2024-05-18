package com.learning.contactweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ContactWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactWebApplication.class, args);
    }



    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/contacts").allowedOrigins("http://localhost:5173").allowedMethods("*");
                registry.addMapping("/**").allowedOrigins("http://localhost:5173").allowedMethods("*");
                registry.addMapping("/contacts/**").allowedOrigins("http://localhost:5173").allowedMethods("*");
            }
        };
    }

}