package com.untalsanders.contacts.auth.domain.port.in;

public interface LoginUseCase {

    sealed interface LoginResult permits LoginResult.TokensIssued, LoginResult.MfaChallengeRequired {
        record TokensIssued(String accessToken, String refreshToken, long expiresIn) implements LoginResult {}
        record MfaChallengeRequired(String challengeToken) implements LoginResult {}
    }

    LoginResult login(String email, String password);
}
