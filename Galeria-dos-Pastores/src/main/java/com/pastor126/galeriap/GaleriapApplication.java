package com.pastor126.galeriap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GaleriapApplication {

    public static void main(String[] args) {
        SpringApplication.run(GaleriapApplication.class, args);
    }
}
