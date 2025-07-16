package com.integradorII.backend.tests;

import com.integradorII.backend.controller.FallaController;
import com.integradorII.backend.model.EstadoFalla;
import com.integradorII.backend.model.Falla;
import com.integradorII.backend.service.IFallaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FallaControllerTest {

    @Mock
    private IFallaService fallaService;

    @InjectMocks
    private FallaController fallaController;

    private Falla falla;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        falla = new Falla();
        falla.setId(1L);
        falla.setDescripcion("Motor dañado");
        falla.setEstado(EstadoFalla.PENDIENTE);
        falla.setFechaRegistro(LocalDateTime.now());
    }

    @Test
    void testCrear() {
        when(fallaService.registrarFalla(falla)).thenReturn(falla);

        Falla result = fallaController.crear(falla);

        assertNotNull(result);
        assertEquals("Motor dañado", result.getDescripcion());
        verify(fallaService).registrarFalla(falla);
    }

    @Test
    void testActualizar() {
        when(fallaService.buscarPorId(1L)).thenReturn(Optional.of(falla));
        when(fallaService.registrarFalla(any(Falla.class))).thenReturn(falla);

        Falla actualizada = new Falla();
        actualizada.setDescripcion("Motor reparado");
        actualizada.setEstado(EstadoFalla.RESUELTO);
        actualizada.setFechaActualizacion(LocalDateTime.now());

        Falla result = fallaController.actualizar(1L, actualizada);

        assertEquals("Motor reparado", result.getDescripcion());
        assertEquals(EstadoFalla.RESUELTO, result.getEstado());
        verify(fallaService).registrarFalla(any(Falla.class));
    }

    @Test
    void testActualizar_NoEncontrada() {
        when(fallaService.buscarPorId(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            fallaController.actualizar(1L, falla);
        });

        assertTrue(exception.getMessage().contains("Falla no encontrada"));
    }

    @Test
    void testEliminar() {
        doNothing().when(fallaService).eliminarFalla(1L);

        fallaController.eliminar(1L);

        verify(fallaService).eliminarFalla(1L);
    }

    @Test
    void testListarTodas() {
        when(fallaService.listarTodas()).thenReturn(List.of(falla));

        List<Falla> result = fallaController.listarTodas();

        assertEquals(1, result.size());
        verify(fallaService).listarTodas();
    }

    @Test
    void testPorMaquina() {
        when(fallaService.filtrarPorMaquina("Molino")).thenReturn(List.of(falla));

        List<Falla> result = fallaController.porMaquina("Molino");

        assertEquals(1, result.size());
        verify(fallaService).filtrarPorMaquina("Molino");
    }

    @Test
    void testPorTecnico() {
        when(fallaService.filtrarPorTecnico(1L)).thenReturn(List.of(falla));

        List<Falla> result = fallaController.porTecnico(1L);

        assertEquals(1, result.size());
        verify(fallaService).filtrarPorTecnico(1L);
    }

    @Test
    void testPorFechas() {
        LocalDateTime desde = LocalDateTime.now().minusDays(1);
        LocalDateTime hasta = LocalDateTime.now();

        when(fallaService.filtrarPorFecha(desde, hasta)).thenReturn(List.of(falla));

        List<Falla> result = fallaController.porFechas(desde.toString(), hasta.toString());

        assertEquals(1, result.size());
        verify(fallaService).filtrarPorFecha(desde, hasta);
    }

    @Test
    void testListarPendientes() {
        falla.setEstado(EstadoFalla.PENDIENTE);
        when(fallaService.listarTodas()).thenReturn(List.of(falla));

        List<Falla> result = fallaController.listarPendientes();

        assertEquals(1, result.size());
        assertEquals(EstadoFalla.PENDIENTE, result.get(0).getEstado());
        verify(fallaService).listarTodas();
    }

    @Test
    void testListarEnProceso() {
        falla.setEstado(EstadoFalla.EN_PROCESO);
        when(fallaService.listarTodas()).thenReturn(List.of(falla));

        List<Falla> result = fallaController.listarEnProceso();

        assertEquals(1, result.size());
        assertEquals(EstadoFalla.EN_PROCESO, result.get(0).getEstado());
        verify(fallaService).listarTodas();
    }
}

