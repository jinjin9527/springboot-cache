package com.example.demo.cache.customer;

import org.springframework.data.redis.cache.CacheStatistics;
import org.springframework.data.redis.cache.CacheStatisticsCollector;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

public class MyRedisCacheWriter implements RedisCacheWriter {

    private RedisCacheWriter defaultRedisCacheWriter;
    private final RedisConnectionFactory connectionFactory;

    public MyRedisCacheWriter(RedisConnectionFactory redisConnectionFactory){
        this.connectionFactory = redisConnectionFactory;
        this.defaultRedisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
    }
    @Override
    public void put(String name, byte[] key, byte[] value, Duration ttl) {
        System.out.println("my put");
        this.defaultRedisCacheWriter.put(name, key, value, ttl);
    }

    @Override
    public byte[] get(String name, byte[] key) {
        System.out.println("my get");
        return this.defaultRedisCacheWriter.get(name, key);
    }

    @Override
    public byte[] putIfAbsent(String name, byte[] key, byte[] value, Duration ttl) {
        return this.defaultRedisCacheWriter.putIfAbsent(name, key, value, ttl);
    }

    @Override
    public void remove(String name, byte[] key) {
        this.defaultRedisCacheWriter.remove(name, key);
    }

    @Override
    public void clean(String name, byte[] pattern) {
        this.defaultRedisCacheWriter.clean(name, pattern);
    }

    @Override
    public void clearStatistics(String name) {
        this.defaultRedisCacheWriter.clearStatistics(name);
    }

    @Override
    public RedisCacheWriter withStatisticsCollector(CacheStatisticsCollector cacheStatisticsCollector) {
        return this.defaultRedisCacheWriter.withStatisticsCollector(cacheStatisticsCollector);
    }

    @Override
    public CacheStatistics getCacheStatistics(String cacheName) {
        return this.defaultRedisCacheWriter.getCacheStatistics(cacheName);
    }
}
