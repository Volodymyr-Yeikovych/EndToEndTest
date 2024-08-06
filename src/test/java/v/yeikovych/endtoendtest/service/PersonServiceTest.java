package v.yeikovych.endtoendtest.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import v.yeikovych.endtoendtest.dto.PersonDto;
import v.yeikovych.endtoendtest.model.Person;
import v.yeikovych.endtoendtest.repository.PersonRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    BlacklistService blacklistService;

    @Mock
    EmailService emailService;

    @Mock
    PersonRepository personRepository;

    PersonService personService;

    @BeforeEach
    public void setup() {
        personService = new PersonService(personRepository, emailService, blacklistService);
    }

    @Test
    public void shouldGetPersonById() {
        var id = UUID.randomUUID();
        var person = mock(Person.class);
        var captor = ArgumentCaptor.forClass(UUID.class);
        when(personRepository.getById(captor.capture())).thenReturn(Optional.of(person));

        assertThat(personService.getById(id)).isEqualTo(person);
        assertThat(captor.getValue()).isEqualTo(id);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionIfInvalidEmail() {
        var id = UUID.randomUUID();
        var captor = ArgumentCaptor.forClass(UUID.class);
        when(blacklistService.isBlacklistedId(captor.capture())).thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class, () -> personService.getById(id));
        assertThat(captor.getValue()).isEqualTo(id);
        assertThat(exception).hasMessage("Cannot get blacklisted id [" + captor.getValue() + "].");
    }

    @Test
    public void shouldThrowNoSuchElementExceptionIfPersonNotFoundInRepository() {
        var id = UUID.randomUUID();
        var captor = ArgumentCaptor.forClass(UUID.class);
        when(personRepository.getById(captor.capture())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> personService.getById(id));
        assertThat(captor.getValue()).isEqualTo(id);
    }

    @Test
    public void shouldAddPersonToRepository() {
        var id = UUID.randomUUID();
        var name = "Vasa";
        var email = "vasa@gmail.com";
        var dto = new PersonDto(name, email);
        var person = new Person(id, name, email);
        var captor = ArgumentCaptor.forClass(Person.class);
        when(personRepository.add(captor.capture())).thenReturn(person);

        Person addedPerson = personService.add(dto);
        assertThat(addedPerson).isEqualTo(person);
        assertThat(captor.getValue().getName()).isEqualTo(name);
        assertThat(captor.getValue().getEmail()).isEqualTo(email);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForInvalidEmail() {
        var name = "Vasa";
        var email = "invalid email";
        var dto = new PersonDto(name, email);
        when(emailService.isInvalidEmail(email)).thenReturn(true);

        var exception = assertThrows(IllegalArgumentException.class, () -> personService.add(dto));
        assertThat(exception).hasMessage("Invalid email: [" + email + "].");
    }
}
