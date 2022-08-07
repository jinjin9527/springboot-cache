package com.example.demo.transaction.service;

import com.example.demo.transaction.entity.Persons;
import com.example.demo.transaction.repository.PersonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonsRepository personsRepository;

    @Transactional
    public List<Persons> getPersons(String city, String firstName) {
        List<Persons> firstRows = personsRepository.getPersonsByCity(city);
        List<Persons> lastRows = personsRepository.getPersonsByFirstName(firstName);
        firstRows.get(0).setFirstName(firstRows.get(0).getFirstName() + city);
        personsRepository.save(firstRows.get(0));
        if(lastRows.size() == 1) {
            throw new RuntimeException("2");
        }

        firstRows.addAll(lastRows);

        return firstRows;
    }
}
