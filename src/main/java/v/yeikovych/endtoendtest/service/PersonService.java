package v.yeikovych.endtoendtest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import v.yeikovych.endtoendtest.dto.PersonDto;
import v.yeikovych.endtoendtest.model.Person;
import v.yeikovych.endtoendtest.repository.PersonRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository repository;
    private final EmailService emailService;
    private final BlacklistService blacklistService;

    public Person getById(UUID id) throws NoSuchElementException, IllegalArgumentException {
        if (blacklistService.isBlacklistedId(id)) {
            log.info("Invalid Operation. Cannot get blacklisted id [{}]", id);
            throw new IllegalArgumentException("Cannot get blacklisted id [" + id + "].");
        }

        return repository.getById(id).orElseThrow();
    }

    public void add(PersonDto dto) throws IllegalArgumentException {
        if (emailService.isInvalidEmail(dto.email())) {
            log.info("Person with name and email[{}, {}] was not added, invalid email.", dto.name(), dto.email());
            throw new IllegalArgumentException("Invalid email: [" + dto.email() + "].");
        }

        repository.add(new Person(null, dto.name(), dto.email()));
    }
}
