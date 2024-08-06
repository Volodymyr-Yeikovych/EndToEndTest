package v.yeikovych.endtoendtest.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public boolean isInvalidEmail(String email) {
        return email.contains("5");
    }
}
