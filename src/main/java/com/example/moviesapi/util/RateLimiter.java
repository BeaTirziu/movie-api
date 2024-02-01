package com.example.moviesapi.util;

import java.time.Duration;

import org.springframework.stereotype.Component;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

@Component
public class RateLimiter {

    private final Bandwidth bandwidth;
    private final Bucket bucket;

    // TO DO: Should be configurable
    private final long rateLimitPerSecond = 50;

    public RateLimiter() {
        this.bandwidth = Bandwidth.simple(rateLimitPerSecond, Duration.ofSeconds(1));
        this.bucket = Bucket.builder()
                .addLimit(bandwidth)
                .build();
    }

    public boolean tryConsume() {
        return bucket.tryConsume(1);
    }
}
