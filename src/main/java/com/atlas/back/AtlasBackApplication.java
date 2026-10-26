package com.atlas.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AtlasBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtlasBackApplication.class, args);
    }

}
