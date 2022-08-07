package com.example.demo.transaction.repository;

import com.example.demo.transaction.entity.Persons;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonsRepository extends CrudRepository<Persons, Integer> {
    @Cacheable(value="itemRedisCache", key="#firstName")
    List<Persons> getPersonsByFirstName(String firstName);
    @Cacheable(value="itemRedisCache", key="#lastName")
    List<Persons> getPersonsByLastName(String lastName);
    @Cacheable(value="itemRedisCache", key="#city")
    List<Persons> getPersonsByCity(String city);
    @Cacheable(value="itemRedisCache", key="#address")
    List<Persons> getPersonsByAddress(String address);
}
