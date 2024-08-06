package v.yeikovych.endtoendtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import v.yeikovych.endtoendtest.dto.PersonDto;
import v.yeikovych.endtoendtest.model.Person;
import v.yeikovych.endtoendtest.repository.PersonRepository;

import javax.management.InstanceAlreadyExistsException;
import java.util.NoSuchElementException;

@Service
public class PersonService {

    private final PersonRepository repository;
    private int nextId = 0;

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Person getById(int id) throws NoSuchElementException {
        return repository.getById(id).orElseThrow();
    }

    public void add(PersonDto dto) throws IllegalArgumentException {
        repository.add(new Person(nextId++, dto.getName(), dto.getEmail()));
    }
}
