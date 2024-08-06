package v.yeikovych.endtoendtest.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import v.yeikovych.endtoendtest.model.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class PersonRepository {

    private static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);
    private static final Map<Integer, Person> personDb = new HashMap<>();

    public Optional<Person> getById(int id) {
        return Optional.ofNullable(personDb.get(id));
    }

    public void add(Person person) throws IllegalArgumentException {
        if (personDb.containsKey(person.id())) {
            logger.info("Person [" + person + "] already exists, cannot add.");
            throw new IllegalArgumentException("Person [" + person + "] already exists.");
        }

        personDb.put(person.id(), person);
        logger.info("Person [" + person + "] successfully added to db.");
    }
}
