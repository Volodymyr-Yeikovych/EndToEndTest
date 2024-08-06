package v.yeikovych.endtoendtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import v.yeikovych.endtoendtest.dto.PersonDto;

import v.yeikovych.endtoendtest.service.PersonService;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    private final PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getPersonById(@PathVariable int id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping
    public ResponseEntity<?> addPerson(@RequestBody PersonDto dto) {
        service.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
