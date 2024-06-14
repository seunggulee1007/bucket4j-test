package com.yeseung.buckettest;

import com.giffing.bucket4j.spring.boot.starter.context.RateLimiting;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private static final Logger log = LoggerFactory.getLogger(TestService.class);

    @RateLimiting(
        name = "not_an_admin",
        cacheKey = "#username",
        executeCondition = "#username != 'admin'",
        skipCondition = "#username eq 'admin'",
        ratePerMethod = true,
        fallbackMethodName = "myFallbackMethod"

    )
    public String execute(String username) {
        log.info("Method with Param {} executed", username);
        return username;
    }

    public String myFallbackMethod(String username) {
        log.info("myFallbackMethod with Param {} executed", username);
        return username;
    }

}
