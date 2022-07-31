package com.example.demo.cache.controller;

import com.example.demo.cache.entity.CacheForm;
import com.example.demo.cache.entity.TransferForm;
import com.example.demo.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cache")
public class CacheController {
    @Autowired
    private CacheService cacheService;

    @PostMapping("/test_inmemory_cache")
    public List<String> test_cache1(@RequestBody CacheForm cacheForm){
        return cacheService.getInMemoryCacheContent(cacheForm);
    }

    @PostMapping("/test_redis_cache")
    public List<String> test_cache2(@RequestBody CacheForm cacheForm){
        return cacheService.getItemRedisCache(cacheForm);
    }

    @PostMapping("/test_redis_lua")
    public List<String> test_cache3(@RequestBody TransferForm transferForm){
        return cacheService.transfer(transferForm);
    }

    @PostMapping("/test_redis_keygenerator")
    public List<String> test_cache4(@RequestBody CacheForm cacheForm){
        return cacheService.getKeyGenerator(cacheForm);
    }
}
