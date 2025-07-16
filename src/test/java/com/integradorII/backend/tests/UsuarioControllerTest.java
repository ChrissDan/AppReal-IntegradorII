package com.integradorII.backend.tests;

import com.integradorII.backend.model.Rol;
import com.integradorII.backend.model.Usuario;
import com.integradorII.backend.controller.UsuarioController;
import com.integradorII.backend.service.IUsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private IUsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setCodigoEmpleado("EMP123");
        usuario.setUsername("juanp");
        usuario.setPassword("12345");
        usuario.setRol(Rol.TECNICO);
    }

    @Test
    void testRegistrar() {
        when(usuarioService.guardarUsuario(usuario)).thenReturn(usuario);

        Usuario result = usuarioController.registrar(usuario);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
        verify(usuarioService).guardarUsuario(usuario);
    }

    @Test
    void testListar() {
        List<Usuario> lista = Arrays.asList(usuario);
        when(usuarioService.listarUsuarios()).thenReturn(lista);

        List<Usuario> result = usuarioController.listar();

        assertEquals(1, result.size());
        verify(usuarioService).listarUsuarios();
    }

    @Test
    void testBuscarPorId() {
        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioController.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getNombre());
        verify(usuarioService).buscarPorId(1L);
    }

    @Test
    void testBuscarPorUsername() {
        when(usuarioService.buscarPorUsername("juanp")).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioController.buscarPorUsername("juanp");

        assertTrue(result.isPresent());
        assertEquals("juanp", result.get().getUsername());
        verify(usuarioService).buscarPorUsername("juanp");
    }

    @Test
    void testActualizar() {
        Usuario actualizado = new Usuario();
        actualizado.setNombre("Pedro");
        actualizado.setApellido("Lopez");
        actualizado.setCodigoEmpleado("EMP456");
        actualizado.setUsername("pedrol");
        actualizado.setRol(Rol.SUPERVISOR);

        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(usuario));
        when(usuarioService.guardarUsuario(any(Usuario.class))).thenReturn(actualizado);

        Usuario result = usuarioController.actualizar(1L, actualizado);

        assertNotNull(result);
        assertEquals("Pedro", result.getNombre());
        verify(usuarioService).guardarUsuario(any(Usuario.class));
    }

    @Test
    void testEliminar() {
        doNothing().when(usuarioService).eliminarUsuario(1L);

        usuarioController.eliminar(1L);

        verify(usuarioService).eliminarUsuario(1L);
    }

    @Test
    void testValidarCodigoEmpleado() {
        when(usuarioService.listarUsuarios()).thenReturn(List.of(usuario));

        Map<String, Boolean> result = usuarioController.validarCodigoEmpleado("EMP123");

        assertTrue(result.get("existe"));
        verify(usuarioService).listarUsuarios();
    }

    @Test
    void testValidarUsername() {
        when(usuarioService.buscarPorUsername("juanp")).thenReturn(Optional.of(usuario));

        Map<String, Boolean> result = usuarioController.validarUsername("juanp");

        assertTrue(result.get("existe"));
        verify(usuarioService).buscarPorUsername("juanp");
    }

    @Test
    void testListarTecnicos() {
        Usuario otro = new Usuario();
        otro.setId(2L);
        otro.setNombre("Ana");
        otro.setRol(Rol.SUPERVISOR);

        when(usuarioService.listarUsuarios()).thenReturn(List.of(usuario, otro));

        List<Usuario> tecnicos = usuarioController.listarTecnicos();

        assertEquals(1, tecnicos.size());
        assertEquals(Rol.TECNICO, tecnicos.get(0).getRol());
    }

    @Test
    void testActualizarPassword() {
        Map<String, String> body = new HashMap<>();
        body.put("password", "nueva123");

        when(usuarioService.buscarPorId(1L)).thenReturn(Optional.of(usuario));
        when(usuarioService.guardarUsuario(any(Usuario.class))).thenReturn(usuario);

        Usuario result = usuarioController.actualizarPassword(1L, body);

        assertNotNull(result);
        assertEquals("nueva123", usuario.getPassword());
        verify(usuarioService).guardarUsuario(any(Usuario.class));
    }
}
