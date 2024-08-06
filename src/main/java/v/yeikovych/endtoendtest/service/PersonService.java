package v.yeikovych.endtoendtest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import v.yeikovych.endtoendtest.dto.PersonDto;
import v.yeikovych.endtoendtest.model.Person;
import v.yeikovych.endtoendtest.repository.PersonRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public Person getById(UUID id) throws NoSuchElementException {
        return repository.getById(id).orElseThrow();
    }

    public void add(PersonDto dto) throws IllegalArgumentException {
        repository.add(new Person(null, dto.name(), dto.email()));
    }
}
