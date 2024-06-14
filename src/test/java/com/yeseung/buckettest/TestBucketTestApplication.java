package com.yeseung.buckettest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
public class TestBucketTestApplication {

    public static void main(String[] args) {
        SpringApplication.from(BucketTestApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
