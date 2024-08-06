package v.yeikovych.endtoendtest.repository;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public Person add(String name, String email) throws IllegalArgumentException {
        Person person = new Person(nextId++, name, email);

        synchronized (PERSON_DB) {
            if (PERSON_DB.containsKey(person.id())) {
                log.info("Person [{}] already exists, cannot add.", person);
                throw new IllegalArgumentException("Person [" + person + "] already exists.");
            }

            PERSON_DB.put(person.id(), person);
            log.info("Person [{}] successfully added to db.", person);
            return person;
        }
    }
}
