package com.project.sentinel_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimitingService {
    private final StringRedisTemplate stringRedisTemplate;

    private static final int MAX_REQUEST_PER_MINUTE = 60;

    public boolean isValid(Long clientId){
        String key = "rate_limit" + clientId;

        Long count = stringRedisTemplate.opsForValue().increment(key);

        if (count != null && count == 1){
            stringRedisTemplate.expire(key, Duration.ofMinutes(1));
        }

        return count != null && count <= MAX_REQUEST_PER_MINUTE;
    }


}
