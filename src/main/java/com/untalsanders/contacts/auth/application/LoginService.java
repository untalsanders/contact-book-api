package com.untalsanders.contacts.auth.application;

import com.untalsanders.contacts.auth.domain.exception.InvalidCredentialsException;
import com.untalsanders.contacts.auth.domain.port.in.LoginUseCase;
import com.untalsanders.contacts.security.jwt.JwtUtil;
import com.untalsanders.contacts.user.domain.model.User;
import com.untalsanders.contacts.user.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class LoginService implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResult login(String email, String rawPassword) {
        var user = userRepository.findByEmail(email).orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return issuedTokens(user);
    }

    LoginResult.TokensIssued issuedTokens(User user) {
        return new LoginResult.TokensIssued("accessToken", "refreshToken", 0);
    }
}
