package com.finanzas_backend_spring.user_system.util;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfiguration {
    @Bean(name = "financeApi")
    public OpenAPI financeApi() {
        return new OpenAPI().components(new Components()).info(new Info().title("Finance API")
                .description("Finance API implemented with Spring Boot RESTful service and documented using springdoc-openapi"));
    }
}
