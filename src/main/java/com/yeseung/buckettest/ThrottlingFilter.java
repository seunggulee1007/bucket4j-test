package com.yeseung.buckettest;

import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

public class ThrottlingFilter extends OncePerRequestFilter {

    private Bucket createNewBucket() {
        return Bucket.builder()
            .addLimit(limit -> limit.capacity(10).refillGreedy(10, Duration.ofSeconds(1)))
            .build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
        ServletException,
        IOException {
        HttpSession session = request.getSession(true);

        String appKey = request.getParameter("test");
        Bucket bucket = (Bucket)session.getAttribute("throttler-" + appKey);

        if(bucket == null) {
            bucket = createNewBucket();
            session.setAttribute("throttler-" + appKey, bucket);
        }

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setContentType("application/json");
            response.setStatus(429);
            response.getWriter().append("{\"success\": \"false\", \"message\": \"요청횟수가 초과 되었습니다.\"}");
            response.flushBuffer();
        }
    }

}
