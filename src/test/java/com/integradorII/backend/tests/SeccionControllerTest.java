package com.integradorII.backend.tests;

import com.integradorII.backend.model.Seccion;
import com.integradorII.backend.controller.SeccionController;
import com.integradorII.backend.repository.SeccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeccionControllerTest {

    @Mock
    private SeccionRepository seccionRepository;

    @InjectMocks
    private SeccionController seccionController;

    private Seccion seccion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seccion = new Seccion();
        seccion.setId(1L);
        seccion.setNombre("Producción");
    }

    @Test
    void testListar() {
        when(seccionRepository.findAll()).thenReturn(List.of(seccion));

        List<Seccion> result = seccionController.listar();

        assertEquals(1, result.size());
        assertEquals("Producción", result.get(0).getNombre());
        verify(seccionRepository).findAll();
    }

    @Test
    void testCrear() {
        when(seccionRepository.save(seccion)).thenReturn(seccion);

        Seccion result = seccionController.crear(seccion);

        assertNotNull(result);
        assertEquals("Producción", result.getNombre());
        verify(seccionRepository).save(seccion);
    }

    @Test
    void testActualizar() {
        Seccion actualizada = new Seccion();
        actualizada.setNombre("Embalaje");

        when(seccionRepository.findById(1L)).thenReturn(Optional.of(seccion));
        when(seccionRepository.save(any(Seccion.class))).thenReturn(actualizada);

        Seccion result = seccionController.actualizar(1L, actualizada);

        assertEquals("Embalaje", result.getNombre());
        verify(seccionRepository).save(any(Seccion.class));
    }

    @Test
    void testActualizar_NoEncontrada() {
        when(seccionRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            seccionController.actualizar(1L, seccion);
        });

        assertTrue(exception.getMessage().contains("Sección no encontrada"));
    }

    @Test
    void testEliminar() {
        doNothing().when(seccionRepository).deleteById(1L);

        seccionController.eliminar(1L);

        verify(seccionRepository).deleteById(1L);
    }
}

