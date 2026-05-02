package com.satvik.equipment.service;

import com.satvik.equipment.config.JwtService;
import com.satvik.equipment.dto.request.LoginRequest;
import com.satvik.equipment.dto.request.RegisterRequest;
import com.satvik.equipment.dto.response.AuthResponse;
import com.satvik.equipment.entity.User;
import com.satvik.equipment.enums.Role;
import com.satvik.equipment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("satvik");
        registerRequest.setPassword("Test1234!");
        registerRequest.setRole(Role.ADMIN);

        loginRequest = new LoginRequest();
        loginRequest.setUsername("satvik");
        loginRequest.setPassword("Test1234!");

        user = User.builder()
                .username("satvik")
                .password("hashedpassword")
                .role(Role.ADMIN)
                .build();
    }

    @Test
    void register_ShouldReturnAuthResponse_WhenUsernameIsNew() {
        when(userRepository.existsByUsername("satvik")).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedpassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any())).thenReturn("mock.jwt.token");

        AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("satvik", response.getUsername());
        assertEquals("ADMIN", response.getRole());
        assertEquals("mock.jwt.token", response.getToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_ShouldThrowException_WhenUsernameExists() {
        when(userRepository.existsByUsername("satvik")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.register(registerRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_ShouldReturnAuthResponse_WhenCredentialsValid() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userRepository.findByUsername("satvik")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("mock.jwt.token");

        AuthResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("satvik", response.getUsername());
        assertEquals("mock.jwt.token", response.getToken());
    }

    @Test
    void login_ShouldThrowException_WhenUserNotFound() {
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));
    }
}