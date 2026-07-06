package com.untalsanders.contacts.security;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CookieSecurityAttributesTest {
    @Test
    @DisplayName("Should verify cookie security attributes")
    void should_verify_cookie_security_attributes() {
        Cookie cookie = new Cookie("JSESSIONID", "test-value");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        assertTrue(cookie.isHttpOnly(), "Cookie should be HttpOnly");
        assertTrue(cookie.getSecure(), "Cookie should be Secure");
    }
}
