package v.yeikovych.endtoendtest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import v.yeikovych.endtoendtest.dto.PersonDto;

import v.yeikovych.endtoendtest.service.PersonService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService service;

    @GetMapping("{id}")
    public ResponseEntity<?> getPersonById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping
    public ResponseEntity<?> addPerson(@RequestBody PersonDto dto) {
        service.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
