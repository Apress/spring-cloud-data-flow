package com.apress.spring.directory.controller;

import com.apress.spring.directory.domain.Person;
import com.apress.spring.directory.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class PersonController {

    private Logger log = LoggerFactory.getLogger(PersonController.class);
    private PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @RequestMapping(value = "/people",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public Iterable<Person> getPeople() {
        log.info("Accessing all Directory people...");
        return personRepository.findAll();
    }

    @RequestMapping(value = "/people",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> create(UriComponentsBuilder uriComponentsBuilder, @RequestBody Person person) {
        personRepository.save(person);

        UriComponents uriComponents =
                uriComponentsBuilder.path("/people/{id}").buildAndExpand(person.getEmail());

        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @RequestMapping(value = "/people/search",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<?> findByEmail(@RequestParam String email) {
        log.info("Looking for {}", email);
        return ResponseEntity.ok(personRepository.findById(email).orElse(null));
    }

    @RequestMapping(value = "/people/{email:.+}",
            method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteByEmail(@PathVariable String email) {
        log.info("About to delete {}", email);
        personRepository.deleteById(email);
        return ResponseEntity.accepted().build();
    }
}
