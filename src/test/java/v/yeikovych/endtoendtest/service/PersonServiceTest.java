package v.yeikovych.endtoendtest.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

    @InjectMocks
    PersonService personService;

    @Test
    public void shouldGetPersonById() {
        var id = UUID.randomUUID();
        var person = mock(Person.class);
        var captor = ArgumentCaptor.forClass(UUID.class);
        when(personRepository.getById(captor.capture())).thenReturn(Optional.of(person));

        assertThat(personService.getById(id)).isEqualTo(person);
        assertThat(captor.getValue()).isEqualTo(id);
        verify(personRepository).getById(captor.getValue());
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
    public void shouldThrowNoSuchElementExceptionIfPersonNotFoundInDb() {
        var id = UUID.randomUUID();
        var captor = ArgumentCaptor.forClass(UUID.class);
        when(personRepository.getById(captor.capture())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> personService.getById(id)).isInstanceOf(NoSuchElementException.class);
        assertThat(captor.getValue()).isEqualTo(id);
        verify(personRepository).getById(captor.getValue());
    }
}
