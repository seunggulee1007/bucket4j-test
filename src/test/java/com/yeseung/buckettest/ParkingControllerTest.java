package com.yeseung.buckettest;

import com.yeseung.buckettest.parking.ParkingApplyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.stream.IntStream;

@SpringBootTest(properties = {
    "bucket4j.filters[0].cache-name=redis-redisson",
    "bucket4j.filters[0].filter-method=webflux",
    "bucket4j.filters[0].rate-limits[0].bandwidths[0].capacity=5",
    "bucket4j.filters[0].rate-limits[0].bandwidths[0].time=10",
    "bucket4j.filters[0].rate-limits[0].bandwidths[0].unit=seconds",
    "bucket4j.filters[0].rate-limits[0].bandwidths[0].refill-speed=interval",
    "bucket4j.filters[0].url=^(/api/parking).*",
})
@AutoConfigureWebTestClient
@AutoConfigureMockMvc
@DirtiesContext
class ParkingControllerTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    WebTestClient rest;

    @Test
    @Order(1)
    @DisplayName("주차권 양도 처리율 테스트")
    void parkingTest() {
        String url = "/api/parking";
        IntStream.rangeClosed(1, 5)
            .boxed()
            .sorted(Collections.reverseOrder())
            .forEach(counter -> successfulWebRequest(url, counter - 1));

        blockedWebRequestDueToRateLimit(url);
    }

    private void blockedWebRequestDueToRateLimit(String url) {
        rest
            .post()
            .uri(url)
            .body(Mono.just(getApplyRequest()), ParkingApplyRequest.class)
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.TOO_MANY_REQUESTS)
            .expectBody().jsonPath("error", "Too many requests!");
    }

    private void successfulWebRequest(String url, Integer remainingTries) {
        rest
            .post()
            .uri(url)
            .body(Mono.just(getApplyRequest()), ParkingApplyRequest.class)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().valueEquals("X-Rate-Limit-Remaining", String.valueOf(remainingTries));
    }

    public ParkingApplyRequest getApplyRequest() {
        return ParkingApplyRequest.builder()
            .carNo("07로3725")
            .maxHm("1000")
            .maxYmd("20240628")
            .playhubYn("N")
            .playmuseumYn("Y")
            .transferYmd("20240628")
            .build();
    }

}
