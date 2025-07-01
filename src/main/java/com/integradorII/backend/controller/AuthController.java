package com.integradorII.backend.controller;

import com.integradorII.backend.model.Usuario;
import com.integradorII.backend.repository.UsuarioRepository;
import com.integradorII.backend.security.AuthRequest;
import com.integradorII.backend.security.AuthResponse;
import com.integradorII.backend.security.JWTUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Obtenemos el usuario y su rol desde la base de datos
            Usuario user = usuarioRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            String rol = user.getRol().name(); // JEFE, SUPERVISOR, TECNICO
            String token = jwtUtil.generateToken(
                    user.getUsername(),
                    rol,
                    user.getId(),
                    user.getNombre(),
                    user.getApellido()
                    );

            return new AuthResponse(token);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Credenciales inválidas");
        }
    }
}
