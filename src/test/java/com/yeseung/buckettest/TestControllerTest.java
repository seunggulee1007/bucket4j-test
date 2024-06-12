package com.yeseung.buckettest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("")
    void test50 () throws Exception {
        // given
        try(ExecutorService executorService = Executors.newFixedThreadPool(50)){
            CountDownLatch countDownLatch = new CountDownLatch(50);
            for(int i=0; i<55; i++) {
                executorService.submit(()-> {
                    try {
                        mockMvc.perform(get("/test").param("test", "Test")).andDo(print());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }finally {
                        countDownLatch.countDown();
                    }
                });
            }
        };
        // when

        // then

    }
}