package com.edukatorplus;

import com.edukatorplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EdukatorplusApplication implements CommandLineRunner {
    @Autowired UserService userService;
    public static void main(String[] args) {
        SpringApplication.run(EdukatorplusApplication.class, args);
    }
    @Override
    public void run(String... args) {
        userService.seedAdmin("admin@ngo.com", "adminpass123", "Admin", "NGO");
    }
}
