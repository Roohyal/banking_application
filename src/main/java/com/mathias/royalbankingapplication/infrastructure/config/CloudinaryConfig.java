package com.mathias.royalbankingapplication.infrastructure.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {



 @Bean
    public Cloudinary cloudinary() {
     Map<String, String> config = new HashMap<>();

     config .put("cloud_name", System.getenv("ROYAL_CLOUD"));
     config.put("api_secret", System.getenv("ROYAL_SECRET"));
     config.put("api_key", System.getenv("ROYAL_API"));

     return new Cloudinary(config);
 }
}