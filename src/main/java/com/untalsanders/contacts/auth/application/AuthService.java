package com.untalsanders.contacts.auth.application;

import com.untalsanders.contacts.security.jwt.JwtUtil;
import com.untalsanders.contacts.auth.web.dto.AuthResponse;
import com.untalsanders.contacts.auth.web.dto.LoginRequest;
import com.untalsanders.contacts.user.domain.model.User;
import com.untalsanders.contacts.user.domain.port.out.UserRepository;
import com.untalsanders.contacts.user.infrastructure.persistence.mapper.UserPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserPersistenceMapper userMapper;

    public AuthResponse login(LoginRequest request) {
        // Autenticar el usuario
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        // Buscar el usuario (dominio)
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        log.info("User logged in successfully: {}", request.getEmail());

        // Generar token JWT usando la entidad mapeada que implementa UserDetails
        String jwtToken = jwtUtil.generateToken(userMapper.toEntity(user));

        return AuthResponse.builder()
            .token(jwtToken)
            .email(user.getEmail())
            .firstname(user.getFirstname())
            .lastname(user.getLastname())
            .role(user.getRole().name())
            .build();
    }
}
