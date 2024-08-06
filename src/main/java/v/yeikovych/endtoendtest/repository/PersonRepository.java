package v.yeikovych.endtoendtest.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import v.yeikovych.endtoendtest.model.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class PersonRepository {

    private static final Map<Integer, Person> PERSON_DB = new HashMap<>();
    private int nextId = 0;

    public Optional<Person> getById(int id) {
        return Optional.ofNullable(PERSON_DB.get(id));
    }

    public synchronized Person add(Person person) throws IllegalArgumentException {
        person.setId(nextId++);

        if (PERSON_DB.containsKey(person.getId())) {
            log.info("Person [{}] already exists, cannot add.", person);
            throw new IllegalArgumentException("Person [" + person + "] already exists.");
        }

        PERSON_DB.put(person.getId(), person);
        log.info("Person [{}] successfully added to db.", person);
        return person;
    }
}
