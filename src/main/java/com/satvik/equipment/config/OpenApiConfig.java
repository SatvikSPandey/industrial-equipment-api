package com.satvik.equipment.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Industrial Equipment & Work Order Management API",
        version = "1.0",
        description = "REST API for managing industrial equipment, maintenance work orders, and maintenance logs. Built with Spring Boot 3.5, Spring Security JWT, and PostgreSQL.",
        contact = @Contact(
            name = "Satvik Pandey",
            url = "https://satvikspandey.netlify.app"
        )
    )
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class OpenApiConfig {
}