package com.app.trackme;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableBatchProcessing
@EnableFeignClients
public class TrackMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackMeApplication.class, args);
    }

}

