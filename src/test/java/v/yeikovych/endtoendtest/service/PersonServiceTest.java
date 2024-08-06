package v.yeikovych.endtoendtest.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import v.yeikovych.endtoendtest.model.Person;
import v.yeikovych.endtoendtest.repository.PersonRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    BlacklistService blacklistService;

    @Mock
    EmailService emailService;

    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonService personService;

    private Person invalidPerson;
    private Person validPerson;

    private UUID validUuid;
    private UUID blacklistedUuid;

    private String invalidEmail;
    private String validEmail;

    @BeforeEach
    public void beforeEach() {
        validUuid = UUID.randomUUID();
        blacklistedUuid = UUID.randomUUID();
        invalidEmail = "email5";
        validEmail = "email";
        validPerson = new Person(validUuid, "John", validEmail);
        invalidPerson = new Person(blacklistedUuid, "John", invalidEmail);
    }

    @Test
    public void shouldGetPersonById() {
        when(personRepository.getById(validUuid)).thenReturn(Optional.of(validPerson));

        assertThat(personService.getById(validUuid)).isEqualTo(validPerson);
        verify(personRepository).getById(validUuid);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfInvalidEmail() {
        when(blacklistService.isBlacklistedId(blacklistedUuid)).thenReturn(true);

        assertThatThrownBy(() -> personService.getById(blacklistedUuid))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot get blacklisted id [" + blacklistedUuid + "].");
    }

    @Test
    public void shouldThrowNoSuchElementExceptionIfPersonNotFoundInDb() {
        when(personRepository.getById(validUuid)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> personService.getById(validUuid))
                .isInstanceOf(NoSuchElementException.class);
        verify(personRepository).getById(validUuid);
    }
}
