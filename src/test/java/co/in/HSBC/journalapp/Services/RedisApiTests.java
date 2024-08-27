package co.in.HSBC.journalapp.Services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisApiTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    public void testRedisApi() {
        redisTemplate.opsForValue().set("email", "shreep300@gmail.com");
        Object email = redisTemplate.opsForValue().get("desired-salary");
        int i = 1;
    }
}
