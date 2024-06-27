package com.yeseung.buckettest.parking;

import com.giffing.bucket4j.spring.boot.starter.context.RateLimiting;
import org.springframework.stereotype.Service;

@Service
public class ParkingApplyService {

    @RateLimiting(
        // reference to the property file
        name = "parking",
        // the rate limit is per user
        cacheKey = "#request.carNo",
        // the method name is added to the cache key to  prevent conflicts with other methods
        ratePerMethod = true,
        // if the limit is exceeded the fallback method is called. If not provided an exception is thrown
        fallbackMethodName = "myFallbackMethod")
    public ParkingApplyResponse apply(ParkingApplyRequest request) {
        return request.convertResponse();
    }

}
