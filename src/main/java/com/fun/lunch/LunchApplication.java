package com.fun.lunch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LunchApplication {

    public static void main(String[] args) {
        SpringApplication.run(LunchApplication.class, args);
    }

}