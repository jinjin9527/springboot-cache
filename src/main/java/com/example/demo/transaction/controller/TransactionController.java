package com.example.demo.transaction.controller;

import com.example.demo.transaction.entity.Persons;
import com.example.demo.transaction.repository.PersonsRepository;
import com.example.demo.transaction.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private PersonsRepository personsRepository;

    @Autowired
    private PersonService personService;

    @PostMapping("/addPerson")
    public @ResponseBody String addPerson(@RequestBody Persons persons){
        personsRepository.save(persons);
        return "saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Persons> getAllUsers() {
        // This returns a JSON or XML with the users
        return personsRepository.findAll();
    }

    @PostMapping(path="/conditions")
    public @ResponseBody Iterable<Persons> getUsersByConditions(@RequestBody Persons persons) {
        return personService.getPersons(persons.getCity(), persons.getFirstName());
    }
}
