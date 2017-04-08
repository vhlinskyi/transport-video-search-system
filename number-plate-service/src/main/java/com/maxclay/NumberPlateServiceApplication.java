package com.maxclay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NumberPlateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NumberPlateServiceApplication.class, args);
    }

}
