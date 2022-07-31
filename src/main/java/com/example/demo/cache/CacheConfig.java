package com.example.demo.cache;

import com.example.demo.cache.customer.CustomKeyGenerator;
import com.example.demo.cache.customer.MyRedisCacheWriter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class CacheConfig {

    @Bean(name = "redisCache")
    @Primary
    public CacheManager cacheManager(RedisCacheWriter myRedisCacheWriter) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(60))
            .disableCachingNullValues()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder()
                .withCacheConfiguration("itemRedisCache", redisCacheConfiguration)
                .cacheWriter(myRedisCacheWriter).build();
    }


    @Bean(name = "inMemoryCache")
    public CacheManager inMemoryCache() {
        SimpleCacheManager cache = new SimpleCacheManager();
        return cache;
    }

    @Bean
    public RedisCacheWriter myRedisCacheWriter(RedisConnectionFactory redisConnectionFactory){
        return new MyRedisCacheWriter(redisConnectionFactory);
    }

    @Bean
    public RedisScript<Boolean> script() {
        Resource scriptSource = new ClassPathResource("scripts/moneyTransfer.lua");
        return RedisScript.of(scriptSource, Boolean.class);
    }

    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new CustomKeyGenerator();
    }
}
