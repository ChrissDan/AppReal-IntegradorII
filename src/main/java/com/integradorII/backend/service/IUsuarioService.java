package com.integradorII.backend.service;

import com.integradorII.backend.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    Usuario guardarUsuario(Usuario usuario);
    List<Usuario> listarUsuarios();
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorUsername(String username);
}
