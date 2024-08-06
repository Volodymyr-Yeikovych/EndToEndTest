package v.yeikovych.endtoendtest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import v.yeikovych.endtoendtest.model.Person;
import v.yeikovych.endtoendtest.repository.PersonRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonControllerTest {

    @Autowired
    public PersonController personController;

    @Autowired
    public PersonRepository personRepository;

    @Test
    public void getShouldPass(){
        var name = "name";
        var email = "email";
        var saved = personRepository.add(new Person(null, name, email));

        var actual = personController.getPersonById(saved.getId());
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assert body, but for same reason it's void
    }
}