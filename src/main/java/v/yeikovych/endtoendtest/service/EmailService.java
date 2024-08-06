package v.yeikovych.endtoendtest.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final String FORBIDDEN_STR = "5";

    public boolean isInvalidEmail(String email) {
        return email.contains(FORBIDDEN_STR);
    }
}
