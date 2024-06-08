package com.aleksandrm.mynotions.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(name = "Aleksandr", email = "test@test.com", url = "https://test.com"),
                description = "OpenApi documentation for Spring security",
                title = "OpenAi specification",
                version = "1.0.0",
                license = @License(name = "License name", url = "https://test.com"),
                termsOfService = "Terms of studying"
        ),
        servers = {
                @Server(description = "Local ENV", url = "http://localhost:8088/api/v1"),
                @Server(description = "Production ENV", url = "https://test.com")
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class openApiConfig {
}
