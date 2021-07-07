package com.ust.microservices.timeSheet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class TimeSheetApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeSheetApplication.class, args);
    }

    @Bean
    public RestTemplate restTempate() {
        return new RestTemplate();
    }

}