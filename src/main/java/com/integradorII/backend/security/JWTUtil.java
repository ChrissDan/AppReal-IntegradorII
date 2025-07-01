package com.integradorII.backend.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {
    private final String SECRET = "clave-secreta";
    private final long EXPIRATION = 1000 * 60 * 60 * 10; // 10 horas

    public String generateToken(String username, String rol, Long id, String nombre, String apellido) {
        return Jwts.builder()
                .setSubject(username)
                .claim("rol", rol)
                .claim("id", id)
                .claim("nombre", nombre)
                .claim("apellido", apellido)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

