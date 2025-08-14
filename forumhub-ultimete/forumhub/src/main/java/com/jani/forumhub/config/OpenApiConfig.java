package com.jani.forumhub.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI api(){
        return new OpenAPI().info(new Info()
                .title("FórumHub API")
                .version("v1")
                .description("API do desafio FórumHub (Alura/Oracle)"));
    }
}
