package com.untalsanders.contacts.auth.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private String firstname;
    private String lastname;
    private String role;
    @Builder.Default
    private String type = "Bearer";
}

