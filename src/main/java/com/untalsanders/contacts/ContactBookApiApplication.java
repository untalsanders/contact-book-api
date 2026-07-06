package com.untalsanders.contacts;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContactBookApiApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ContactBookApiApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    @Bean
    CommandLineRunner init() {
        return args -> {
            System.out.println("Contact Book API is running...");
        };
    }
}
