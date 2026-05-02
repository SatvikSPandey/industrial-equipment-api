package com.satvik.equipment.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        jwtService.jwtSecret = "KtcD7ZGEYnj0WUalJLkeFImChV13dfzS8irR2yBwgPNH5xX9AMToQvqps4Ob6u";
        jwtService.jwtExpiryMs = 86400000L;
        userDetails = User.builder()
                .username("testuser")
                .password("password")
                .authorities(Collections.emptyList())
                .build();
    }

    @Test
    void generateToken_ShouldReturnNonNullToken() {
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        String token = jwtService.generateToken(userDetails);
        String username = jwtService.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    void isTokenValid_ShouldReturnTrueForValidToken() {
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void isTokenValid_ShouldReturnFalseForWrongUser() {
        String token = jwtService.generateToken(userDetails);
        UserDetails otherUser = User.builder()
                .username("otheruser")
                .password("password")
                .authorities(Collections.emptyList())
                .build();
        assertFalse(jwtService.isTokenValid(token, otherUser));
    }

    @Test
    void generateToken_ShouldProduceDifferentTokensForDifferentUsers() {
        UserDetails anotherUser = User.builder()
                .username("anotheruser")
                .password("password")
                .authorities(Collections.emptyList())
                .build();
        String token1 = jwtService.generateToken(userDetails);
        String token2 = jwtService.generateToken(anotherUser);
        assertNotEquals(token1, token2);
    }
}
