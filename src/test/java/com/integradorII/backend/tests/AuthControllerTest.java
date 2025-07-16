package com.integradorII.backend.tests;

import com.integradorII.backend.model.Usuario;
import com.integradorII.backend.controller.AuthController;
import com.integradorII.backend.model.Rol;
import com.integradorII.backend.repository.UsuarioRepository;
import com.integradorII.backend.security.AuthRequest;
import com.integradorII.backend.security.AuthResponse;
import com.integradorII.backend.security.JWTUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_ValidCredentials_ReturnsToken() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setUsername("ronnvas");
        request.setPassword("123");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("ronnvas");
        usuario.setNombre("Ronny");
        usuario.setApellido("Vasquez");
        usuario.setRol(Rol.JEFE);

        when(usuarioRepository.findByUsername("ronnvas")).thenReturn(Optional.of(usuario));
        when(jwtUtil.generateToken(
                eq("ronnvas"),
                eq("JEFE"),
                eq(1L),
                eq("Ronny"),
                eq("Vasquez")
        )).thenReturn("mocked-jwt-token");

        // Act
        AuthResponse response = authController.login(request);

        // Assert
        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getToken());

        // Verify
        verify(authManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(usuarioRepository).findByUsername("ronnvas");
        verify(jwtUtil).generateToken("ronnvas", "JEFE", 1L, "Ronny", "Vasquez");
    }

    @Test
    void login_InvalidCredentials_ThrowsException() {
        // Arrange
        AuthRequest request = new AuthRequest();
        request.setUsername("invaliduser");
        request.setPassword("wrongpass");

        doThrow(new AuthenticationException("Credenciales inválidas") {}).when(authManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authController.login(request);
        });

        assertEquals("Credenciales inválidas", exception.getMessage());

        // Verify
        verify(authManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(usuarioRepository);
        verifyNoInteractions(jwtUtil);
    }
}

