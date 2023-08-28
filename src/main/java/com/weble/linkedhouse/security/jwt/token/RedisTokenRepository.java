package com.weble.linkedhouse.security.jwt.token;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisTokenRepository {

    private final StringRedisTemplate stringRedisTemplate;

    public void save(String email, String token) {
        stringRedisTemplate.opsForValue().set(email, token, 1209600, TimeUnit.SECONDS);
    }

    public String find(String email) {
        return stringRedisTemplate.opsForValue().get(email);
    }

    public void deleteToken(String email) {
        stringRedisTemplate.delete(email);
    }
}
