package com.integradorII.backend.tests;

import com.integradorII.backend.model.Maquina;
import com.integradorII.backend.model.Seccion;
import com.integradorII.backend.controller.MaquinaController;
import com.integradorII.backend.repository.MaquinaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MaquinaControllerTest {

    @Mock
    private MaquinaRepository maquinaRepository;

    @InjectMocks
    private MaquinaController maquinaController;

    private Maquina maquina;
    private Seccion seccion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        seccion = new Seccion();
        seccion.setId(1L);
        seccion.setNombre("Producción");

        maquina = new Maquina();
        maquina.setId(1L);
        maquina.setNombre("Molino");
        maquina.setSeccion(seccion);
    }

    @Test
    void testListar() {
        when(maquinaRepository.findAll()).thenReturn(List.of(maquina));

        List<Maquina> result = maquinaController.listar();

        assertEquals(1, result.size());
        assertEquals("Molino", result.get(0).getNombre());
        verify(maquinaRepository).findAll();
    }

    @Test
    void testPorSeccion() {
        when(maquinaRepository.findBySeccionId(1L)).thenReturn(List.of(maquina));

        List<Maquina> result = maquinaController.porSeccion(1L);

        assertEquals(1, result.size());
        verify(maquinaRepository).findBySeccionId(1L);
    }

    @Test
    void testBuscarPorId() {
        when(maquinaRepository.findById(1L)).thenReturn(Optional.of(maquina));

        Optional<Maquina> result = maquinaController.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Molino", result.get().getNombre());
        verify(maquinaRepository).findById(1L);
    }

    @Test
    void testCrear() {
        when(maquinaRepository.save(maquina)).thenReturn(maquina);

        Maquina result = maquinaController.crear(maquina);

        assertNotNull(result);
        assertEquals("Molino", result.getNombre());
        verify(maquinaRepository).save(maquina);
    }

    @Test
    void testEliminar() {
        doNothing().when(maquinaRepository).deleteById(1L);

        maquinaController.eliminar(1L);

        verify(maquinaRepository).deleteById(1L);
    }

    @Test
    void testActualizar() {
        Maquina actualizada = new Maquina();
        actualizada.setNombre("Quebrantadora");
        actualizada.setSeccion(seccion);

        when(maquinaRepository.findById(1L)).thenReturn(Optional.of(maquina));
        when(maquinaRepository.save(any(Maquina.class))).thenReturn(actualizada);

        Maquina result = maquinaController.actualizar(1L, actualizada);

        assertEquals("Quebrantadora", result.getNombre());
        verify(maquinaRepository).save(any(Maquina.class));
    }

    @Test
    void testActualizar_NoEncontrada() {
        when(maquinaRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            maquinaController.actualizar(1L, maquina);
        });

        assertTrue(exception.getMessage().contains("Máquina no encontrada"));
    }
}

