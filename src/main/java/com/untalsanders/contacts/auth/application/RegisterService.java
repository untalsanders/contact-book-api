package com.untalsanders.contacts.auth.application;

import com.untalsanders.contacts.auth.domain.port.in.RegisterUseCase;
import com.untalsanders.contacts.auth.infrastructure.security.PasswordHasher;
import com.untalsanders.contacts.user.domain.model.User;
import com.untalsanders.contacts.user.domain.model.UserId;
import com.untalsanders.contacts.user.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RegisterService implements RegisterUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    @Override
    public RegisterResult register(String email, String rawPassword) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Already registered user with email: " + email);
        }

        String username = email.split("@")[0].replaceAll("[^a-zA-Z0-9._-]", "").toLowerCase();

        if (userRepository.existsByUsername(username))
            throw new IllegalArgumentException("Already registered user with the username: " + username);

        String passwordHashed = passwordHasher.hash(rawPassword);
        User user = User.create(null, email, passwordHashed);
        User userSaved = userRepository.save(user);

        return new RegisterResult(userSaved.id().value(), userSaved.email());
    }
}
