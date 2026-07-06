package com.untalsanders.contacts.contact.presentation.rest.auth.service;

import com.untalsanders.contacts.auth.infrastructure.security.PasswordHasher;
import com.untalsanders.contacts.auth.infrastructure.web.dto.AuthResponse;
import com.untalsanders.contacts.auth.infrastructure.web.dto.LoginRequest;
import com.untalsanders.contacts.auth.infrastructure.web.dto.RegisterUserRequest;
import com.untalsanders.contacts.contact.infrastructure.security.JwtService;
import com.untalsanders.contacts.user.domain.model.User;
import com.untalsanders.contacts.user.domain.port.out.UserRepository;
import com.untalsanders.contacts.user.infrastructure.persistence.mapper.UserMapper;
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
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final PasswordHasher passwordEncoder;

    /**
     * Registra un nuevo usuario en el sistema
     */
    public AuthResponse register(RegisterUserRequest request) {
        // Verificar si el usuario ya existe
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Crear nuevo usuario (dominio)
        User newUser = User.create(
            null,
            request.email(),
            passwordEncoder.hash(request.password())
        );

        User savedUser = userRepository.save(newUser);
        log.info("User registered successfully: {}", request.email());

        // Generar token JWT usando la entidad mapeada que implementa UserDetails
        String jwtToken = jwtService.generateToken(userMapper.toEntity(savedUser));

        return AuthResponse.builder()
            .token(jwtToken)
            .email(savedUser.email())
            .firstname(savedUser.firstname())
            .lastname(savedUser.lastname())
            .role(savedUser.role().name())
            .build();
    }

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
        String jwtToken = jwtService.generateToken(userMapper.toEntity(user));

        return AuthResponse.builder()
            .token(jwtToken)
            .email(user.email())
            .firstname(user.firstname())
            .lastname(user.lastname())
            .role(user.role().name())
            .build();
    }
}
