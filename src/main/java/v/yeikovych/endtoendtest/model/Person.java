package v.yeikovych.endtoendtest.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Person {

    private UUID id;
    private String name;
    private String email;

}
