package v.yeikovych.endtoendtest.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import v.yeikovych.endtoendtest.model.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@Slf4j
public class PersonRepository {

    private static final Map<UUID, Person> PERSON_DB = new HashMap<>();

    public Optional<Person> getById(UUID id) {
        return Optional.ofNullable(PERSON_DB.get(id));
    }

    public synchronized Person add(Person person) throws IllegalArgumentException {
        person.setId(UUID.randomUUID());

        if (PERSON_DB.containsKey(person.getId())) {
            log.info("Person [{}] already exists, cannot add.", person);
            throw new IllegalArgumentException("Person [" + person + "] already exists.");
        }

        PERSON_DB.put(person.getId(), person);
        log.info("Person [{}] successfully added to db.", person);
        return person;
    }
}
