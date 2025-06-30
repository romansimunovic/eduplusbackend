package com.edukatorplus.eduplusbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.edukatorplus")
@EnableJpaRepositories(basePackages = "com.edukatorplus.repository") 
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }
}
