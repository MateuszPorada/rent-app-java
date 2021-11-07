package com.example.rentappjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableAsync
public class RentAppJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentAppJavaApplication.class, args);
    }


}
