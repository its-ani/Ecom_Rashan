package dev.anirudh.ecommerce.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T get(String key, Class<T> entityClass) {
        try {
            String value = redisTemplate.opsForValue().get(key);
            if (value == null) return null;
            return mapper.readValue(value, entityClass);
        } catch (Exception e) {
            log.error("Redis GET failed for key {}: {}", key, e.getMessage());
            return null;
        }
    }

    public void set(String key, Object o, Long ttlSeconds) {
        try {
            String jsonValue = mapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, ttlSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis SET failed for key {}: {}", key, e.getMessage());
        }
    }
}
