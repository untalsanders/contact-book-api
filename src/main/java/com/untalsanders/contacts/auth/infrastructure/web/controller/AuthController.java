package com.untalsanders.contacts.auth.infrastructure.web.controller;

import com.untalsanders.contacts.auth.domain.port.in.RegisterUseCase;
import com.untalsanders.contacts.auth.infrastructure.web.dto.AuthResponse;
import com.untalsanders.contacts.auth.infrastructure.web.dto.LoginRequest;
import com.untalsanders.contacts.auth.infrastructure.web.dto.RegisterUserRequest;
import com.untalsanders.contacts.contact.presentation.rest.auth.service.AuthService;
import com.untalsanders.contacts.shared.infrastructure.web.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthController {

    private final AuthService authService;
    private final RegisterUseCase registerUseCase;

    /**
     * Endpoint para registrar un nuevo usuario
     * POST /auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterUseCase.RegisterResult>> register(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUseCase.RegisterResult result = registerUseCase.register(request.email(), request.password());
        ApiResponse<RegisterUseCase.RegisterResult> body = ApiResponse.success(result, "User registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    /**
     * Endpoint para autenticar un usuario
     * POST /auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        ApiResponse<AuthResponse> body = ApiResponse.success(response, "User logged in successfully");
        return ResponseEntity.ok(body);
    }
}

