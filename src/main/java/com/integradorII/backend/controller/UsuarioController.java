package com.integradorII.backend.controller;

import com.integradorII.backend.model.Rol;
import com.integradorII.backend.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.integradorII.backend.service.IUsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@CrossOrigin
public class UsuarioController {

    private final IUsuarioService usuarioService;

    @PostMapping
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioService.guardarUsuario(usuario);
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id);
    }

    @GetMapping("/buscar")
    public Optional<Usuario> buscarPorUsername(@RequestParam String username) {
        return usuarioService.buscarPorUsername(username);
    }

    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id, @RequestBody Usuario actualizado) {
        return usuarioService.buscarPorId(id).map(usuario -> {
            usuario.setNombre(actualizado.getNombre());
            usuario.setApellido(actualizado.getApellido());
            usuario.setCodigoEmpleado(actualizado.getCodigoEmpleado());
            usuario.setUsername(actualizado.getUsername());
            usuario.setRol(actualizado.getRol());
            return usuarioService.guardarUsuario(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }

    // ✅ Validar código de empleado (nuevo)
    @GetMapping("/validar-codigo/{codigoEmpleado}")
    public Map<String, Boolean> validarCodigoEmpleado(@PathVariable String codigoEmpleado) {
        Optional<Usuario> usuario = usuarioService.listarUsuarios()
                .stream()
                .filter(u -> codigoEmpleado.equals(u.getCodigoEmpleado()))
                .findFirst();

        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("existe", usuario.isPresent());
        return respuesta;
    }

    // ✅ Validar username (nuevo)
    @GetMapping("/validar-username/{username}")
    public Map<String, Boolean> validarUsername(@PathVariable String username) {
        Optional<Usuario> usuario = usuarioService.buscarPorUsername(username);

        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("existe", usuario.isPresent());
        return respuesta;
    }

    @GetMapping("/tecnicos")
    public List<Usuario> listarTecnicos() {
        return usuarioService.listarUsuarios()
                .stream()
                .filter(u -> u.getRol() == Rol.TECNICO)
                .toList();
    }
}

