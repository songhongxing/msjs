package com.yuxi.msjs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MsjsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsjsApplication.class, args);
    }

}
