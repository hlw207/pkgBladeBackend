package org.example.domain.Utils;


import org.springframework.stereotype.Component;
import org.mindrot.jbcrypt.BCrypt;


@Component
public class PasswordEncoder {

    public String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}