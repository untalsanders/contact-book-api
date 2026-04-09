package com.untalsanders.contacts.shared.infrastructure.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Contact Book API")
                .version("1.0")
                .description("API for managing a contact book application")
                .contact(swaggerContact()))
            .addSecurityItem(new SecurityRequirement().addList("Authorization"));
    }

    @Bean
    public Contact swaggerContact() {
        return new Contact()
            .name("Sanders Gutiérrez")
            .email("ing.sanders@gmail.com")
            .url("https://github.com/untalsanders/contact-boot-api");
    }
}
