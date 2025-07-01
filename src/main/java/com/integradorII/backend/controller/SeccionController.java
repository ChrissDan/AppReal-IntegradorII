package com.integradorII.backend.controller;

import lombok.RequiredArgsConstructor;
import com.integradorII.backend.model.Seccion;
import org.springframework.web.bind.annotation.*;
import com.integradorII.backend.repository.SeccionRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/secciones")
@RequiredArgsConstructor
@CrossOrigin
public class SeccionController {

    private final SeccionRepository seccionRepository;

    @GetMapping
    public List<Seccion> listar() {
        return seccionRepository.findAll();
    }

    @PostMapping
    public Seccion crear(@RequestBody Seccion seccion) {
        return seccionRepository.save(seccion);
    }

    // ✅ Actualizar sección existente
    @PutMapping("/{id}")
    public Seccion actualizar(@PathVariable Long id, @RequestBody Seccion seccionActualizada) {
        Optional<Seccion> seccionOptional = seccionRepository.findById(id);

        if (seccionOptional.isPresent()) {
            Seccion seccion = seccionOptional.get();
            seccion.setNombre(seccionActualizada.getNombre());
            // si agregas más atributos en la entidad, aquí los actualizarías

            return seccionRepository.save(seccion);
        } else {
            throw new RuntimeException("Sección no encontrada con id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        seccionRepository.deleteById(id);
    }
}

