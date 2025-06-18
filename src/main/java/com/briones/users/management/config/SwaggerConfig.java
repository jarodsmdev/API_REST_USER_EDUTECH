package com.briones.users.management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info()
                .title("API User Documentation by Leonel Briones")
                .version("1.0.0")
                .description("This is the API documentation for the User Management Service. " +
                        "It provides endpoints to manage users, including creating, updating, " +
                        "deleting, and retrieving user information."));
    }
}
