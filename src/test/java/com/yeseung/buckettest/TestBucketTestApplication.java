package com.yeseung.buckettest;

import org.springframework.boot.SpringApplication;

public class TestBucketTestApplication {

    public static void main(String[] args) {
        SpringApplication.from(BucketTestApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
