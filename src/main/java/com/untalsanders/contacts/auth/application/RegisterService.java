package com.untalsanders.contacts.auth.application;

import com.untalsanders.contacts.auth.domain.port.in.RegisterUseCase;
import com.untalsanders.contacts.user.domain.model.User;
import com.untalsanders.contacts.user.domain.model.UserId;
import com.untalsanders.contacts.user.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RegisterService implements RegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResult register(String email, String rawPassword) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Already registered user with email: " + email);
        }

        String username = email.split("@")[0].replaceAll("[^a-zA-Z0-9._-]", "").toLowerCase();

        if (userRepository.existsByUsername(username))
            throw new IllegalArgumentException("Already registered user with the username: " + username);

        String passwordHashed = passwordEncoder.encode(rawPassword);
        var userId = new UserId(java.util.UUID.randomUUID().toString());
        User user = User.create(userId, email, passwordHashed);
        User userSaved = userRepository.save(user);

        return new RegisterResult(userSaved.getId().value(), userSaved.getEmail());
    }
}
