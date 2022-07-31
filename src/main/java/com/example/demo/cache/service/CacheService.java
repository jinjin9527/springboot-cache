package com.example.demo.cache.service;

import com.example.demo.cache.entity.CacheForm;
import com.example.demo.cache.entity.TransferForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheService {

    @Cacheable(value="inMemoryCache", key="#cacheForm.username", cacheManager = "inMemoryCache")
    public List<String> getInMemoryCacheContent(CacheForm cacheForm) {
        return List.of(cacheForm.getUsername() + " :1", cacheForm.getBusinessCode() + " :2");
    }

    @Cacheable(value="itemRedisCache", key="#cacheForm.username", cacheManager = "redisCache")
    public List<String> getItemRedisCache(CacheForm cacheForm) {
        return List.of(cacheForm.getUsername() + " :1", cacheForm.getBusinessCode() + " :2");
    }

    @Cacheable(value="itemRedisCache", keyGenerator="customKeyGenerator", cacheManager = "redisCache")
    public List<String> getKeyGenerator(CacheForm cacheForm) {
        return List.of(cacheForm.getUsername() + " :1", cacheForm.getBusinessCode() + " :2");
    }

    @Autowired
    private RedisScript<Boolean> script;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<String> transfer(TransferForm transferForm){
        this.redisTemplate
                .execute(script, List.of(transferForm.getFromAccount(), transferForm.getToAccount()), String.valueOf(transferForm.getAmount()));
        String fromAccountAmount = (String)this.redisTemplate.opsForHash().get("account", transferForm.getFromAccount());
        String toAccountAmount = (String)this.redisTemplate.opsForHash().get("account", transferForm.getToAccount());

        return List.of(transferForm.getFromAccount() + " : " + fromAccountAmount,
                transferForm.getToAccount() + " : " + toAccountAmount);
    }
}
